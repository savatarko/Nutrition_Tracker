package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import io.reactivex.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.calories.IngredientRepository;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionApiCall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealJSON;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.meal.MealService;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;

public class MealRepository {

    private MealDao localDataSource;
    private MealService remoteDataSource;
    private IngredientRepository ingredientRepository;


    public MealRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        localDataSource = database.mealDao();
        ingredientRepository = new IngredientRepository(application);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteDataSource = retrofit.create(MealService.class);
    }

    public LiveData<List<Meal>> getAll() {
        return localDataSource.getAll();
    }

    public Observable<List<Meal>> fetchAll() {

        List<MealJSON> list;
        List<Meal> meals = new ArrayList<>();
        try {
            list = remoteDataSource.getAllMeals().execute().body().getMeals();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(MealJSON mealJSON : list){
            meals.add(new Meal(Integer.parseInt(mealJSON.getId()), mealJSON.getName(), mealJSON.getThumbnail(), mealJSON.getCategory(), mealJSON.getTags(), mealJSON.getInstructions(), mealJSON.getIngredients(), mealJSON.getMeasures(), mealJSON.getMealarea(), mealJSON.getVideolink(), 0));
        }
        return Observable.just(meals);
    }

    public Observable<List<Meal>> fetchFiltered(MealFilter mealFilter){
        List<MealJSON> list;
        List<Meal> meals = new ArrayList<>();
        try {
            /*if (mealFilter != null && mealFilter.getCategory() != null){
                list = remoteDataSource.getMealsByCategory(mealFilter.getCategory()).execute().body().getMeals();
                List<MealJSON> list2 = new ArrayList<>();
                ExecutorService executorService = Executors.newFixedThreadPool(list.size());
                for(MealJSON mealJSON: list) {
                    //Log.d("TEST", "fetchFiltered: " + remoteDataSource.getMealById(mealJSON.getId()).execute().body().toString());
                    executorService.submit(()->list2.add(remoteDataSource.getMealById(mealJSON.getId()).execute().body().getMeals().get(0)));
                }
                executorService.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
                list = list2;
            }else*/
                list = remoteDataSource.getAllMeals().execute().body().getMeals();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(mealFilter == null) {
            for (MealJSON mealJSON : list) {
                meals.add(new Meal(Integer.parseInt(mealJSON.getId()), mealJSON.getName(), mealJSON.getThumbnail(), mealJSON.getCategory(), mealJSON.getTags(), mealJSON.getInstructions(), mealJSON.getIngredients(), mealJSON.getMeasures(), mealJSON.getMealarea(), mealJSON.getVideolink(), 0));
            }
            return Observable.just(meals);
        }
        for(MealJSON mealJSON : list) {
            Log.d("TEST", "fetchFiltered: "+ mealJSON);
            if (mealFilter.isFromHome() == true) {
                if (mealJSON.getCategory().equalsIgnoreCase(mealFilter.getCategory()) && mealJSON.getName().toLowerCase().contains(mealFilter.getMealName().toLowerCase()) || mealJSON.getIngredients().contains(mealFilter.getIngredient())) {//TODO: ovo trenutno radi samo za jedan sastojak+sastojci jos nisu ni dodati zbog jsona, ovo u filture razdvojiti zarezom                    //float calories = fetchCalories2(mealJSON);
                    float calories = 0;
                    for(int i = 0;i<mealJSON.getIngredients().size();i++){
                        calories+=ingredientRepository.getCalories(mealJSON.getIngredients().get(i), mealJSON.getMeasures().get(i));
                    }
                    if((mealFilter.getMincal()==-1 || calories>=mealFilter.getMincal()) && (mealFilter.getMaxcal()==-1 || calories<=mealFilter.getMaxcal()))
                        meals.add(new Meal(Integer.parseInt(mealJSON.getId()), mealJSON.getName(), mealJSON.getThumbnail(), mealJSON.getCategory(), mealJSON.getTags(), mealJSON.getInstructions(), mealJSON.getIngredients(), mealJSON.getMeasures(), mealJSON.getMealarea(), mealJSON.getVideolink(), calories));
                }
            } else {
                if (mealJSON.getName().toLowerCase().contains(mealFilter.getMealName().toLowerCase()) && (mealFilter.getCategory().equals("") || mealJSON.getCategory().equalsIgnoreCase(mealFilter.getCategory())) &&
                        (mealFilter.getArea().equals("") || mealJSON.getMealarea().equalsIgnoreCase(mealFilter.getArea())) && (mealFilter.getIngredient().equals("") || checkIngredients(mealJSON, mealFilter.getIngredient())) && (mealFilter.getTag().equals("") || mealJSON.getTags().contains(mealFilter.getTag()))) {
                    //float calories = fetchCalories2(mealJSON);
                    float calories = 0;
                    for(int i = 0;i<mealJSON.getIngredients().size();i++){
                        calories+=ingredientRepository.getCalories(mealJSON.getIngredients().get(i), mealJSON.getMeasures().get(i));
                    }
                    if((mealFilter.getMincal()==-1 || calories>=mealFilter.getMincal()) && (mealFilter.getMaxcal()==-1 || calories<=mealFilter.getMaxcal()))
                        meals.add(new Meal(Integer.parseInt(mealJSON.getId()), mealJSON.getName(), mealJSON.getThumbnail(), mealJSON.getCategory(), mealJSON.getTags(), mealJSON.getInstructions(), mealJSON.getIngredients(), mealJSON.getMeasures(), mealJSON.getMealarea(), mealJSON.getVideolink(), calories));
                }
            }
        }
        if(mealFilter.isSorted()){
            meals = meals.stream().sorted((m1,m2)->{
                return m1.name.compareTo(m2.name);
            }).collect(Collectors.toList());
        }
        if(mealFilter.isCalsort()){
            meals = meals.stream().sorted((m1,m2)->{
                return Float.compare(m1.calories,m2.calories);
            }).collect(Collectors.toList());
        }
        if(meals.size() ==0){
            return Observable.just(meals);
        }
        mealFilter.setIndex(Math.max(0, mealFilter.getIndex()));
        int startindex = mealFilter.getIndex()* mealFilter.getMaxperpage();
        if(startindex>=meals.size()){
            mealFilter.setIndex(mealFilter.getIndex()-1);
        }
        startindex = mealFilter.getIndex()* mealFilter.getMaxperpage();
        int endindex = (mealFilter.getIndex() + 1)* mealFilter.getMaxperpage();
        endindex = Math.min(endindex, meals.size());
        return Observable.just(meals.subList(startindex, endindex));
    }

    private boolean checkIngredients(MealJSON meal, String ingredient){
        for(String s : meal.getIngredients()){
            if(s.toLowerCase().contains(ingredient))
                return true;
        }
        return false;
    }

    private float fetchCalories2(MealJSON meal){

        List<String> ingredients = meal.getIngredients();
        List<String> measures = meal.getMeasures();
        String query = "";
        for (int i = 0; i < ingredients.size(); i++) {
            query += measures.get(i) + " " + ingredients.get(i) + ", ";
        }
        float calories = 0;

        String finalQuery = query;

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

    public List<Meal> getAllByCategory(String category) {
        return localDataSource.getAllByCategory(category);
    }

    public List<Meal> getAllList(){
        return localDataSource.getAllList();
    }

    public Meal getById(int id){
        return localDataSource.getById(id);
    }
}
