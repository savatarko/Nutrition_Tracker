package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
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



    @SerializedName("strIngredient1")
    public String ingredient1;
    @SerializedName("strIngredient2")
    public String ingredient2;
    @SerializedName("strIngredient3")
    public String ingredient3;
    @SerializedName("strIngredient4")
    public String ingredient4;
    @SerializedName("strIngredient5")
    public String ingredient5;
    @SerializedName("strIngredient6")
    public String ingredient6;
    @SerializedName("strIngredient7")
    public String ingredient7;
    @SerializedName("strIngredient8")
    public String ingredient8;
    @SerializedName("strIngredient9")
    public String ingredient9;
    @SerializedName("strIngredient10")
    public String ingredient10;
    @SerializedName("strIngredient11")
    public String ingredient11;
    @SerializedName("strIngredient12")
    public String ingredient12;
    @SerializedName("strIngredient13")
    public String ingredient13;
    @SerializedName("strIngredient14")
    public String ingredient14;
    @SerializedName("strIngredient15")
    public String ingredient15;
    @SerializedName("strIngredient16")
    public String ingredient16;
    @SerializedName("strIngredient17")
    public String ingredient17;
    @SerializedName("strIngredient18")
    public String ingredient18;
    @SerializedName("strIngredient19")
    public String ingredient19;
    @SerializedName("strIngredient20")
    public String ingredient20;

    @SerializedName("strMeasure1")
    public String measure1;
    @SerializedName("strMeasure2")
    public String measure2;
    @SerializedName("strMeasure3")
    public String measure3;
    @SerializedName("strMeasure4")
    public String measure4;
    @SerializedName("strMeasure5")
    public String measure5;
    @SerializedName("strMeasure6")
    public String measure6;
    @SerializedName("strMeasure7")
    public String measure7;
    @SerializedName("strMeasure8")
    public String measure8;
    @SerializedName("strMeasure9")
    public String measure9;
    @SerializedName("strMeasure10")
    public String measure10;
    @SerializedName("strMeasure11")
    public String measure11;
    @SerializedName("strMeasure12")
    public String measure12;
    @SerializedName("strMeasure13")
    public String measure13;
    @SerializedName("strMeasure14")
    public String measure14;
    @SerializedName("strMeasure15")
    public String measure15;
    @SerializedName("strMeasure16")
    public String measure16;
    @SerializedName("strMeasure17")
    public String measure17;
    @SerializedName("strMeasure18")
    public String measure18;
    @SerializedName("strMeasure19")
    public String measure19;
    @SerializedName("strMeasure20")
    public String measure20;

    public List<String> getIngredients(){
        List<String> ingredients = new ArrayList<>(Arrays.asList(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9, ingredient10, ingredient11, ingredient12, ingredient13, ingredient14, ingredient15, ingredient16, ingredient17, ingredient18, ingredient19, ingredient20));
        //remove empty strings from ingredients
        ingredients.removeIf(s -> s == null || s.isEmpty());
        return ingredients;
    }

    public List<String> getMeasures(){
        List<String> measures = new ArrayList<>(Arrays.asList(measure1, measure2, measure3, measure4, measure5, measure6, measure7, measure8, measure9, measure10, measure11, measure12, measure13, measure14, measure15, measure16, measure17, measure18, measure19, measure20));
        //remove empty strings from measures
        measures.removeIf(s -> s == null || s.isEmpty() || s.equals(" "));
        return measures;
    }
}
