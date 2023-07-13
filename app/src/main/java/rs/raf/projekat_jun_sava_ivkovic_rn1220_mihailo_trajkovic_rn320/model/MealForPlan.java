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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public MealName getMealName() {
        return mealName;
    }

    public void setMealName(MealName mealName) {
        this.mealName = mealName;
    }
}
