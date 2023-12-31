package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.MealRepository;

public class MealDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Meal> meal = new MutableLiveData<>();

    public LiveData<Meal> getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal.setValue(meal);
    }

    public MealDetailsViewModel(@NonNull Application application, int id) {
        super(application);
//        AppDatabase db = AppDatabase.getInstance(application);
//        Meal meal2 = db.mealDao().getById(id);
//        meal.setValue(meal2);
    }

}
