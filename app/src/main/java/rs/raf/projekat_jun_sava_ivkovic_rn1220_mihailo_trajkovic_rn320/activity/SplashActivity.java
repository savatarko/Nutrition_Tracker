package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user.User;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.CategoryAPICall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.CategoryJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadCategories();
        if(!checkLogin())
            loadDatabase();
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private boolean checkLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("login", -1);
        if(id!=-1){
            return true;
        }
        return false;
    }

    private void loadDatabase(){
        try {
            /*
            Adding a test user if he doesn't exist
            username: test
            pass: Test123
             */
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            User user = db.userDao().findUser("test","Test123");
            if(user==null){
                db.userDao().insert(new User("test", "Test123"));
            }
            //db.close();

            Intent intent = new Intent(this, LoginActivity.class);
            //wait(1);
            startActivity(intent);
        } catch (Exception e) {
            Log.d("database_err", e.toString());
        }
    }

    private void loadCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryAPICall categoryAPICall = retrofit.create(CategoryAPICall.class);

        Call<CategoryJSON.Categories> call = categoryAPICall.getCategories();
        call.enqueue(new Callback<CategoryJSON.Categories>(){
            @Override
            public void onResponse(Call<CategoryJSON.Categories> call, Response<CategoryJSON.Categories> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error loading categories", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CategoryJSON> categories = null;
                /*
                try {
                    Gson g = new Gson();
                    JSONObject tmp = g.fromJson(response.body(), JSONObject.class);
                    JSONArray jsonArray = tmp.getJSONArray("categories");
                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CategoryJSON category = new CategoryJSON(jsonObject.getString("idCategory"), jsonObject.getString("strCategory"), jsonObject.getString("strCategoryThumb"), jsonObject.getString("strCategoryDescription"));
                        categories.add(category);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                 */
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.categoryDao().deleteAll();
                categories = response.body().getCategories();
                for (CategoryJSON category : categories) {
                    Category category1 = new Category(category.getStrCategory(), category.getStrCategoryThumb(), category.getStrCategoryDescription());
                    db.categoryDao().insert(category1);
                }
            }

            @Override
            public void onFailure(Call<CategoryJSON.Categories> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error loading categories", Toast.LENGTH_SHORT).show();
            }
        });
    }
}