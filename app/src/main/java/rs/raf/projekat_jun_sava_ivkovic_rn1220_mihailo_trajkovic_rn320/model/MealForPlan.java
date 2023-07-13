package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;

public class MealForPlan {

    private String name;
    private String image;
    private String instructions;
    private String videolink;
    private String category;
    private List<String> ingredients;
    private List<String> measures;

    private MealName mealName;

    public MealForPlan(Meal meal, MealName mealName) {
        this.name = meal.name;
        this.image = meal.thumbnail;
        this.instructions = meal.instructions;
        this.videolink = meal.videolink;
        this.category = meal.category;
        this.ingredients = meal.ingredients;
        this.measures = meal.measures;

        this.mealName = mealName;
    }

    public MealForPlan(SavedMeal meal, MealName mealName) {
        this.name = meal.name;
        this.image = meal.image;
        this.instructions = meal.instructions;
        this.videolink = meal.videolink;
        this.category = meal.category;
        this.ingredients = meal.ingredients;
        this.measures = meal.measures;

        this.mealName = mealName;
    }


    @Override
    public String toString() {
        return "MealForPlan{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", instructions='" + instructions + '\'' +
                ", videolink='" + videolink + '\'' +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                ", measures=" + measures +
                ", mealName=" + mealName +
                '}';
    }
}
