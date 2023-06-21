package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPICall {
    @GET("categories.php")
    Call<CategoryJSON.Categories> getCategories();
}
