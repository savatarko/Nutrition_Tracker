package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320;

import android.app.Application;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlanViewModel;

public class AppModule {

    private static AppModule am;

    private MealPlanViewModel mealPlanViewModel;

    public static AppModule getInstance() {
        if(am == null) {
            am = new AppModule();
        }
        return am;
    }


    private AppModule() {

    }

    public MealPlanViewModel getMealPlanViewModel() {
        return mealPlanViewModel;
    }

    public void setMealPlanViewModel(MealPlanViewModel mealPlanViewModel) {
        this.mealPlanViewModel = mealPlanViewModel;
    }


}