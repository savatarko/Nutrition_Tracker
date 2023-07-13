package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.calories;

import android.app.Application;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionApiCall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;

public class IngredientRepository {
    private IngredientDao ingredientDao;

    public IngredientRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        ingredientDao = database.ingredientDao();
    }

    private String[] splithelper(String input){
        String s1 ="", s2="";

        String[] split = input.split(" ");
        if(split.length > 2){
            return new String[]{input};
        }


        int i =0;
        while(i<input.length() && ((input.charAt(i) >='0' && input.charAt(i) <= '9' ) || input.charAt(i)=='/' || input.charAt(i)=='.')){
            i++;
        }
        if(i==0){
            return new String[]{input};
        }
        if(i==input.length()){
            return new String[]{input};
        }
        s1 = input.substring(0, i);
        s2 = input.substring(i, input.length());
        return new String[]{s1, s2};
    }

    public float getCalories(String name, String unit){
        if(name.equals(" ") || unit.equals(" ") || name.equals("") || unit.equals("")){
            return 0;
        }
        List<Ingredient> answer = ingredientDao.getAllByName(name);
        String[] split = splithelper(unit);
        if(split.length == 1){
            for(Ingredient i : answer){
                if(i.unit.equals(unit)){
                    return i.calories;
                }
            }
            //TODO CALL API HERE
            float cal = fetchCalories2(name, unit);
            Ingredient ingredient = new Ingredient(name, cal, unit, 1);
            ingredientDao.insert(ingredient);
            return cal;
        }
        String measure = split[0];
        //unit = split[0];
        String[] tmp = measure.split("/");
        float ammount;
        if(tmp.length > 1){
            ammount = Float.parseFloat(tmp[0]) / Float.parseFloat(tmp[1]);
        }
        else {
            ammount = Float.parseFloat(measure);
        }
        for(Ingredient i : answer){
            if(i.unit.equalsIgnoreCase(split[1])){
                return i.calories * ammount / i.ammount;
            }
        }
        float cal = fetchCalories2(name, unit);
        Ingredient ingredient = new Ingredient(name, cal, split[1], ammount);
        ingredientDao.insert(ingredient);
        return cal;
    }

    private float fetchCalories2(String name, String unit){

        float calories = 0;

        String finalQuery = unit + " " + name;

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NutritionApiCall nutritionApiCall = retrofit2.create(NutritionApiCall.class);
        Call<List<NutritionJSON>> call2 = nutritionApiCall.getCalories(finalQuery);
        Response<List<NutritionJSON>> response2 = null;
        try {
            response2 = call2.execute();
            if(response2.body() == null) return 0;
            for (NutritionJSON nutritionJSON : response2.body())
                calories += nutritionJSON.getCalories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return calories;
    }
}
