package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.MealRepository;

public class MealViewModel extends AndroidViewModel {
    private MealRepository mealRepository;
    private LiveData<List<Meal>> meals;

    public MealViewModel(@NonNull Application application) {
        super(application);
        mealRepository = new MealRepository(application);
        meals = mealRepository.getAll();
    }

    public MealRepository getMealRepository() {
        return mealRepository;
    }
    public LiveData<List<Meal>> getMeals() {
        return meals;
    }
}
