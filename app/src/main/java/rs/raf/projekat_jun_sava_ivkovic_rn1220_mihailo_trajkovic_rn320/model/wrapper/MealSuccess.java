package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.wrapper;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;

public class MealSuccess extends MealWrapper{
    public List<Meal> meals;

    public MealSuccess(List<Meal> meals) {
        this.meals = meals;
    }

    public MealSuccess() {
    }
}
