package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter.MealAdapter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionApiCall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealService;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealViewModel;

public class ListMealsActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MealViewModel mealViewModel;
    private MealAdapter mealAdapter;
    private String category;
    private String searchfilter;
    private Button sortbt;
    private MealFilter mealFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meals);
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        //category = extras.getString("category");
        //searchfilter = extras.getString("filter");
        mealFilter = (MealFilter) extras.getSerializable("filter");
        category = mealFilter.getCategory();
        searchfilter = mealFilter.getMealName();
        init(category);
    }
    private void init(String category){
        initView();
        loadData();
    }
    private void initView(){
        searchView = findViewById(R.id.mealSearchView);
        recyclerView = findViewById(R.id.mealRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        mealAdapter = new MealAdapter();
        recyclerView.setAdapter(mealAdapter);

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        mealViewModel.getMeals().observe(this, meals -> {
            mealAdapter.setMeals(meals);
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
                searchfilter = filter;
                loadData();
                return false;
            }
        });

        sortbt = findViewById(R.id.button3);
        sortbt.setOnClickListener(e->{
            mealAdapter.setMeals(new ArrayList<>());
        });
    }

    private void loadData(){
        mealViewModel.fetchMeals();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.mealDao().deleteAll();
        mealAdapter.setMeals(new ArrayList<>());
    }

    /*
    private void loadFromDb(){
        List<Meal> meals = mealViewModel.getMealRepository().getAllList();
        for(int i =0;i<meals.size();i++){
            if(!meals.get(i).name.toLowerCase().contains(searchfilter.toLowerCase())){
                meals.remove(i);
                i--;
            }
        }
        mealAdapter.setMeals(meals);
    }


     */


}