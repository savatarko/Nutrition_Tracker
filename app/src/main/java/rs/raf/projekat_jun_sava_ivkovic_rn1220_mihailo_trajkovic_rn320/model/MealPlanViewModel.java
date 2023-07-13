package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;

public class MealPlanViewModel {

    MutableLiveData<MealPlan> mealPlan = new MutableLiveData<>();

    MutableLiveData<MealName> mealName = new MutableLiveData<>(MealName.BREAKFAST);
    MutableLiveData<Integer> day = new MutableLiveData<>(0);


    public MealPlanViewModel() {
        mealPlan.setValue(new MealPlan());
    }

    public LiveData<MealPlan> getMealPlan() {
        return mealPlan;
    }

    public void addMeal(SavedMeal savedMeal) {
        MealForPlan meal = new MealForPlan(savedMeal, mealName.getValue());
        addMeal(meal);
    }

    public void addMeal(Meal meal) {
        MealForPlan mealForPlan = new MealForPlan(meal, mealName.getValue());
        addMeal(mealForPlan);
    }

    public void addMeal(MealForPlan mealForPlan) {
        MealPlan mp = mealPlan.getValue();
        mp.setMeal(day.getValue(),mealName.getValue(), mealForPlan);
        mealPlan.setValue(mp);
    }

    public void setMealName(MealName mealName) {
        this.mealName.setValue(mealName);
    }

    public void setDay(int day) {
        this.day.setValue(day);
    }

    public int getDay() {
        return this.day.getValue();
    }

    public MealName getMealName() {
        return this.mealName.getValue();
    }

}
