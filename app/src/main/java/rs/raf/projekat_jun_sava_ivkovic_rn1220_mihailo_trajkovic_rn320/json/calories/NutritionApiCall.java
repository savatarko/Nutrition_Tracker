package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NutritionApiCall {

    @Headers("X-Api-Key: ge+xJ8Mqw0lHW8+clyjH5Q==ltOWjFxIepW4TgX8")
    @GET("nutrition")
    Call<List<NutritionJSON>> getCalories(@Query("query") String query);


}
