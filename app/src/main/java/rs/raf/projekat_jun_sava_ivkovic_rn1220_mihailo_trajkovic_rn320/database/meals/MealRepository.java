package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals;

import android.app.Application;

import androidx.lifecycle.LiveData;

import io.reactivex.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealService;

public class MealRepository {

    private MealDao localDataSource;
    private MealService remoteDataSource;


    public MealRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        localDataSource = database.mealDao();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteDataSource = retrofit.create(MealService.class);
    }

    public LiveData<List<Meal>> getAll() {
        return localDataSource.getAll();
    }

    public Observable<List<Meal>> fetchAll() {

        List<MealJSON> list;
        List<Meal> meals = new ArrayList<>();
        try {
            list = remoteDataSource.getAllMeals().execute().body().getMeals();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(MealJSON mealJSON : list){
            meals.add(new Meal(Integer.parseInt(mealJSON.getId()), mealJSON.getName(), mealJSON.getThumbnail(), mealJSON.getCategory(), mealJSON.getTags(), mealJSON.getInstructions(), mealJSON.getIngredients(), mealJSON.getMeasures(), mealJSON.getMealarea(), mealJSON.getVideolink(), 0));
        }
        return Observable.just(meals);
    }
    public List<Meal> getAllByCategory(String category) {
        return localDataSource.getAllByCategory(category);
    }

    public List<Meal> getAllList(){
        return localDataSource.getAllList();
    }

    public Meal getById(int id){
        return localDataSource.getById(id);
    }
}
