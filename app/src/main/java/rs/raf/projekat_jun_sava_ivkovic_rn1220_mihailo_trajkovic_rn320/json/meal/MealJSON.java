package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class MealJSON {
    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strMealThumb")
    private String thumbnail;

    @SerializedName("strArea")
    private String mealarea;

    // Include other fields as needed

    @SerializedName("strTags")
    private String tags;


    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strYoutube")
    private String videolink;

    /*
    @SerializedName("strIngredient1")
    private List<String> ingredients;

    @SerializedName("strMeasure1")
    private List<String> measures;

     */


    //private Map<String, String> ingredients;


    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public class MealJSONWrapper{
        private List<MealJSON> meals;

        public MealJSONWrapper(List<MealJSON> meals) {
            this.meals = meals;
        }

        public MealJSONWrapper() {
        }

        public List<MealJSON> getMeals() {
            return meals;
        }

        public void setMeals(List<MealJSON> meals) {
            this.meals = meals;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMealarea() {
        return mealarea;
    }

    public void setMealarea(String mealarea) {
        this.mealarea = mealarea;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
