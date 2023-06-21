package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;

public class MealRepository {
    private MealDao mealDao;
    private LiveData<List<Meal>> allMeals;


    public MealRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        mealDao = database.mealDao();
        allMeals = mealDao.getAll();
    }

    public LiveData<List<Meal>> getAll() {
        return allMeals;
    }

    public List<Meal> getAllByCategory(String category) {
        return mealDao.getAllByCategory(category);
    }

    public List<Meal> getAllList(){
        return mealDao.getAllList();
    }
}
