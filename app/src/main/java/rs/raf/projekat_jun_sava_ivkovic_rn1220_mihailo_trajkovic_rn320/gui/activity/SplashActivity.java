package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.calories.IngredientRepository;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user.User;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.category.CategoryAPICall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.category.CategoryJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadCategories();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MealService remoteDataSource;
                IngredientRepository ingredientRepository = new IngredientRepository(getApplication());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.themealdb.com/api/json/v1/1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                remoteDataSource = retrofit.create(MealService.class);
                List<MealJSON> answer;
                try{
                    answer = remoteDataSource.getAllMeals().execute().body().getMeals();
                }
                catch (Exception e){
                    return;
                }
                for(MealJSON mealJSON : answer){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i=0;i<mealJSON.getIngredients().size();i++){
                                ingredientRepository.getCalories(mealJSON.getIngredients().get(i), mealJSON.getMeasures().get(i));
                            }
                        }
                    }).start();
                }
            }
        }).start();
        if(!checkLogin())
            loadDatabase();
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private boolean checkLogin(){
//        return false;
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("login", -1);
        if(id!=-1){
            return true;
        }
        return false;
    }

    private void loadDatabase(){
        try {
            /*
            Adding a test user if he doesn't exist
            username: Pera
            pass: Test123
             */
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            User user = db.userDao().findUser("Pera","Test123");
            if(user==null){
                db.userDao().insert(new User("Pera", "Test123"));
            }
            //db.close();

            Intent intent = new Intent(this, LoginActivity.class);
            //wait(1);
            startActivity(intent);
        } catch (Exception e) {
            Log.d("database_err", e.toString());
        }
    }

    private void loadCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryAPICall categoryAPICall = retrofit.create(CategoryAPICall.class);

        Call<CategoryJSON.Categories> call = categoryAPICall.getCategories();
        call.enqueue(new Callback<CategoryJSON.Categories>(){
            @Override
            public void onResponse(Call<CategoryJSON.Categories> call, Response<CategoryJSON.Categories> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error loading categories", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CategoryJSON> categories = null;
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.categoryDao().deleteAll();
                categories = response.body().getCategories();
                for (CategoryJSON category : categories) {
                    Category category1 = new Category(category.getStrCategory(), category.getStrCategoryThumb(), category.getStrCategoryDescription());
                    db.categoryDao().insert(category1);
                }
            }

            @Override
            public void onFailure(Call<CategoryJSON.Categories> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error loading categories", Toast.LENGTH_SHORT).show();
            }
        });
    }
}