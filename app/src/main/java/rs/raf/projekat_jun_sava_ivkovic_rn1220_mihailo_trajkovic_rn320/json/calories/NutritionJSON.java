package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;

public class NutritionJSON {
    @SerializedName("calories")
    private float calories;

    public class NutritionJSONWrapper{
        private List<NutritionJSON> calories;

        public NutritionJSONWrapper(List<NutritionJSON> calories) {
            this.calories = calories;
        }

        public List<NutritionJSON> getCalories() {
            return calories;
        }

        public void setCalories(List<NutritionJSON> calories) {
            this.calories = calories;
        }
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }
}
