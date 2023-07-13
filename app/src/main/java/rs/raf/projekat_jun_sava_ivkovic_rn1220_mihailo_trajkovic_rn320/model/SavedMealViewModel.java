package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.MealRepository;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMealRepository;

public class SavedMealViewModel extends AndroidViewModel {
    private MutableLiveData<List<SavedMeal>> savedMeals = new MutableLiveData<>();
    private SavedMealRepository savedMealRepository;
    private CompositeDisposable subscriptions = new CompositeDisposable();

    public SavedMealViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        //List<SavedMeal> list = db.savedMealDao().getAll();
        //savedMeals.setValue(list);
        savedMealRepository = new SavedMealRepository(application);
        savedMeals.setValue(savedMealRepository.getAll());
    }

    public void getSavedMeals(MealFilter mealFilter){
        if(mealFilter == null)
            savedMeals.setValue(savedMealRepository.getAll());
        else
            savedMeals.setValue(savedMealRepository.getFiltered(mealFilter));
    }



    public LiveData<List<SavedMeal>> getMeals() {
        return savedMeals;
    }
}
