package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealDetailsViewModel;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MyViewModelFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.databinding.ActivityMealDetailsBinding;

public class MealDetailsActivity extends AppCompatActivity {

    private TextView mealName;
    private TextView mealCategory;
    private TextView mealArea;
    private TextView mealRecipe;
    private TextView video;
    private TextView mealIngredients;
    private TextView tags;
    private ImageView mealImage;
    private Button backbt;
    private Button savebt;

    private int mealid;
    private Meal meal;
    //private Meal meal;

    private MealDetailsViewModel mealDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        mealid = getIntent().getExtras().getInt("mealid");
        meal = (new Gson()).fromJson(getIntent().getExtras().getString("meal"), Meal.class);
        //meal= (Meal) getIntent().getExtras().getSerializable("meal");
        initView();
    }
    private void initView(){

        mealDetailsViewModel = new ViewModelProvider(this, new MyViewModelFactory(this.getApplication(), mealid)).get(MealDetailsViewModel.class);
        mealDetailsViewModel.setMeal(meal);
        ActivityMealDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_details);
        binding.setViewmodel(mealDetailsViewModel);

        binding.setLifecycleOwner(this);


//        mealName = findViewById(R.id.tvMealName);
        mealCategory = findViewById(R.id.tvMealCategory);
        mealArea = findViewById(R.id.tvMealArea);
        mealRecipe = findViewById(R.id.tvMealRecipe);
        video = findViewById(R.id.tvVideo);
        mealIngredients = findViewById(R.id.tvmealIngredients);
        tags = findViewById(R.id.textView10);
        mealImage = findViewById(R.id.imageView2);
        backbt = findViewById(R.id.button5);
        savebt = findViewById(R.id.button2);

        //mealName.setText(meal.name);
        mealCategory.setText(meal.category);
        mealArea.setText(meal.mealArea);
        mealRecipe.setText(meal.instructions);
        //video.setText(meal.);
        //mealIngredients.setText(meal.ingredients);
        tags.setText(meal.tags);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(meal.thumbnail).openConnection();
                    con.connect();
                    InputStream input = con.getInputStream();
                    final Bitmap bm = BitmapFactory.decodeStream(input);
                    mealImage.setImageBitmap(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        savebt.setOnClickListener(e->{
            Intent intent = new Intent(this, SaveMealActivity.class);
            intent.putExtra("mealid", mealid);
            intent.putExtra("meal", (new Gson()).toJson(meal));
            startActivity(intent);
        });

        backbt.setOnClickListener(e->{
            finish();
        });
    }


}