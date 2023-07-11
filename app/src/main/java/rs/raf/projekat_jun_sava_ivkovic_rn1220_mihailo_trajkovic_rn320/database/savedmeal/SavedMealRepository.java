package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal;

import android.app.Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;

public class SavedMealRepository {
    private SavedMealDao savedMealDao;

    public SavedMealRepository(Application application) {
        savedMealDao = AppDatabase.getInstance(application).savedMealDao();
    }

    public List<SavedMeal> getAll(){
        return savedMealDao.getAll();
    }

    public List<SavedMeal> getFiltered(MealFilter mealFilter) {
        List<SavedMeal> list = savedMealDao.getAll();
        List<SavedMeal> meals = new ArrayList<>();
        for (SavedMeal meal : list) {
            if (mealFilter.isFromHome() == true) {
                if (meal.category.equalsIgnoreCase(mealFilter.getCategory()) && meal.name.toLowerCase().contains(mealFilter.getMealName().toLowerCase()) || meal.ingredients.contains(mealFilter.getIngredient())) {//TODO: ovo trenutno radi samo za jedan sastojak+sastojci jos nisu ni dodati zbog jsona, ovo u filture razdvojiti zarezom
                    meals.add(meal);
                }
            } else {
                if (meal.name.toLowerCase().contains(mealFilter.getMealName().toLowerCase()) && (mealFilter.getCategory().equals("") || meal.category.equalsIgnoreCase(mealFilter.getCategory())) &&
                        (mealFilter.getArea().equals("") || meal.area.equalsIgnoreCase(mealFilter.getArea())) && (mealFilter.getIngredient().equals("") || meal.ingredients.contains(mealFilter.getIngredient())) && (mealFilter.getTag().equals("") || meal.tags.contains(mealFilter.getTag()))) {
                    meals.add(meal);
                }
            }
        }
        if(mealFilter.isSorted()){
            meals.sort((o1, o2) -> o1.name.compareTo(o2.name));
        }
        return meals;
    }
}
