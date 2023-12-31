package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter.MealAdapter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter.SavedMealAdapter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionApiCall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealService;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealViewModel;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.SavedMealViewModel;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.wrapper.MealSuccess;

public class ListMealsActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MealViewModel mealViewModel;
    private SavedMealViewModel savedMealViewModel;
    private MealAdapter mealAdapter;
    private SavedMealAdapter savedMealAdapter;
    private String category;
    private String searchfilter;
    private Button sortbt;
    private MealFilter mealFilter;
    private EditText mincal;
    private EditText maxcal;

    private TabLayout tabLayout;
    private Button prevbt;
    private Button nextbt;
    private boolean saved = false;

    private boolean addMeal = false;

    private GifImageView tmpiv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meals);
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        mealFilter = (MealFilter) extras.getSerializable("filter");
        addMeal = extras.getBoolean("addMeal");
        if(mealFilter!=null) {
            category = mealFilter.getCategory();
            searchfilter = mealFilter.getMealName();
        }
        init(category);
    }
    private void init(String category){
        initView();
        loadData();
    }

    private void notifyAboutSwap(){
        if(!saved){
            recyclerView.setAdapter(mealAdapter);
        }
        else{
            recyclerView.setAdapter(savedMealAdapter);
        }
    }
    private void initView(){
        searchView = findViewById(R.id.mealSearchView);
        recyclerView = findViewById(R.id.mealRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        mealAdapter = new MealAdapter(addMeal, this);
        savedMealAdapter = new SavedMealAdapter(addMeal, this);
        recyclerView.setAdapter(mealAdapter);

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        mealViewModel.getMeals().observe(this, meals -> {
            Log.d("TEST", "initView: OBSERVER");
            if(meals instanceof MealSuccess)
                mealAdapter.setMeals(((MealSuccess) meals).meals);
            if(!saved)
                tmpiv.setVisibility(View.GONE);
        });

        savedMealViewModel = new ViewModelProvider(this).get(SavedMealViewModel.class);
        savedMealViewModel.getMeals().observe(this, meals -> {
            savedMealAdapter.setMeals(meals);
            if(saved)
                tmpiv.setVisibility(View.GONE);
        });

        if(searchfilter!=null){
            searchView.setQuery(searchfilter, true);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String filter) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String filter) {
                mealFilter.setMealName(filter);
                loadData();
                return false;
            }
        });

        sortbt = findViewById(R.id.button3);
        sortbt.setOnClickListener(e->{
            mealFilter.setCalsort(!mealFilter.isCalsort());
            loadData();
        });

        tabLayout = findViewById(R.id.tabLayout1);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab t){
                String text = t.getText().toString();
                if(text.equalsIgnoreCase("api")) {
                    saved = false;
                }
                else{
                    saved = true;
                }
                notifyAboutSwap();
                loadData();
                recyclerView.smoothScrollToPosition(0);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab t){
            }
            @Override
            public void onTabReselected(TabLayout.Tab t){
            }
        });
        mincal = findViewById(R.id.calorieLowerBound);
        maxcal = findViewById(R.id.calorieUpperBound);

        mincal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mincal.getText().toString().equalsIgnoreCase("")){
                    mealFilter.setMincal(-1);
                }
                else mealFilter.setMincal(Integer.parseInt(mincal.getText().toString()));
                loadData();
            }
        });

        maxcal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(maxcal.getText().toString().equalsIgnoreCase("")){
                    mealFilter.setMaxcal(-1);
                }
                else mealFilter.setMaxcal(Integer.parseInt(maxcal.getText().toString()));
                loadData();
            }
        });
        prevbt = findViewById(R.id.button12);
        nextbt = findViewById(R.id.button13);
        prevbt.setOnClickListener(e->{
            mealFilter.setIndex(mealFilter.getIndex()-1);
            loadData();
            mealAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(0);
        });
        nextbt.setOnClickListener(e->{
            mealFilter.setIndex(mealFilter.getIndex()+1);
            loadData();
            mealAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(0);
        });

        tmpiv = findViewById(R.id.imageView4);
        //tmpiv.setVisibility(View.GONE);
    }

    private void loadData(){
        tmpiv.setVisibility(View.VISIBLE);
        if(!saved)
            mealViewModel.fetchMeals(mealFilter);
        else savedMealViewModel.getSavedMeals(mealFilter);
    }
}