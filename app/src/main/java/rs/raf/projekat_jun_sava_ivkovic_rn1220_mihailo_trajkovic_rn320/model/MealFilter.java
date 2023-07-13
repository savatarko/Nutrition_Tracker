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
    private int mincal = -1;
    private int maxcal = -1;
    private boolean calsort = false;
    private int index = 0;
    private int maxperpage = 10;

    public boolean isFromHome() {
        return fromHome;
    }

    public void setFromHome(boolean fromHome) {
        this.fromHome = fromHome;
    }

    public MealFilter() {
    }

    public MealFilter(String mealName, String category, String area, String ingredient, String tag, boolean sorted, int maxperpage) {
        this.mealName = mealName;
        this.category = category;
        this.area = area;
        this.ingredient = ingredient;
        this.tag = tag;
        this.sorted = sorted;
        this.maxperpage = maxperpage;
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

    public int getMincal() {
        return mincal;
    }

    public void setMincal(int mincal) {
        this.mincal = mincal;
    }

    public int getMaxcal() {
        return maxcal;
    }

    public void setMaxcal(int maxcal) {
        this.maxcal = maxcal;
    }

    public boolean isCalsort() {
        return calsort;
    }

    public void setCalsort(boolean calsort) {
        this.calsort = calsort;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxperpage() {
        return maxperpage;
    }

    public void setMaxperpage(int maxperpage) {
        this.maxperpage = maxperpage;
    }
}
