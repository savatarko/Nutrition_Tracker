package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.graphics.Typeface;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealPlan {

    private List<Map<MealName,MealForPlan>> mealPlan;

    public MealPlan() {
        mealPlan = new ArrayList<>(7);
        for(int i = 0; i < 7; i++) {
            mealPlan.add(i, new HashMap<>());
        }
    }

    public MealForPlan getMeal(int day, MealName mealName) {
        return mealPlan.get(day).get(mealName);
    }

    public void setMeal(int day, MealName mealName, MealForPlan mealForPlan) {
        mealPlan.get(day).put(mealName, mealForPlan);
    }

    public List<MealForPlan> getMeals(){
        List<MealForPlan> meals = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            for(MealName mealName : MealName.values()) {
                MealForPlan mealForPlan = mealPlan.get(i).get(mealName);
                if(mealForPlan != null) {
                    meals.add(mealForPlan);
                }
            }
        }
        return meals;
    }

    public String parseForEmail(){
        DateFormatSymbols symbols = new DateFormatSymbols();
        symbols.setWeekdays(new String[]{"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 7; i++) {
            sb.append(symbols.getWeekdays()[(i%7)+1] + "\n");
            for(MealName mealName : MealName.values()) {
                MealForPlan mealForPlan = mealPlan.get(i).get(mealName);
                if(mealForPlan != null) {
                    sb.append(mealName.toString() + ": " + mealForPlan.getName() + "\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
