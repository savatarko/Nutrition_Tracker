package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealService {

    @GET("filter.php")
    Call<MealJSON.MealJSONWrapper> getMealsByCategory(@Query("c") String category);

//    @GET("filter.php?c=:Beef")
//    Call<MealJSON.MealJSONWrapper> getMealsByCategory();

    @GET("search.php?s=")
    Call<MealJSON.MealJSONWrapper> getAllMeals();
}
