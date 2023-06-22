package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import java.io.Serializable;

public class MealFilter implements Serializable {
    private String mealName ="";
    private String category = "";
    private String area = "";
    private String ingredient = "";
    private String tag = "";
    private boolean sorted = false;
    private boolean fromHome = false;

    public boolean isFromHome() {
        return fromHome;
    }

    public void setFromHome(boolean fromHome) {
        this.fromHome = fromHome;
    }

    public MealFilter() {
    }

    public MealFilter(String mealName, String category, String area, String ingredient, String tag, boolean sorted) {
        this.mealName = mealName;
        this.category = category;
        this.area = area;
        this.ingredient = ingredient;
        this.tag = tag;
        this.sorted = sorted;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }
}
