package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.MealRepository;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionApiCall;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.json.calories.NutritionJSON;

public class MealViewModel extends AndroidViewModel {
    private MealRepository mealRepository;
    private MutableLiveData<List<Meal>> meals;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    public MealViewModel(@NonNull Application application) {
        super(application);
        mealRepository = new MealRepository(application);
//        meals = mealRepository.getAll();
        meals = new MutableLiveData<>();
        //fetchMeals(new MealFilter());
    }

    public void fetchMeals(MealFilter mealFilter){
        Thread thread = new Thread(()->fetchMeals2(mealFilter));
        thread.start();
    }

    private void fetchMeals2(MealFilter mealFilter){
        List<Meal> mock = new ArrayList<>();
        //mock.add(new Meal(1,"asd","asd","asd","asd","asd",new ArrayList<>(),new ArrayList<>(),"asd","asd",0));
        Disposable subscription =
                mealRepository.fetchFiltered(mealFilter)
                .startWith(Observable.just(mock))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            this.meals.setValue(new ArrayList<>(meals));
                            for(Meal meal : this.meals.getValue()){
                                fetchCalories(meal);
                            }
                        }
                );
        subscriptions.add(subscription);
    }

    private void fetchCalories(Meal meal){
        Thread thread = new Thread(()-> meal.calories = fetchCalories2(meal));
        thread.start();
            //meals.postValue(meals.getValue());
    }
    public MealRepository getMealRepository() {
        return mealRepository;
    }
    public LiveData<List<Meal>> getMeals() {
        return meals;
    }


    private float fetchCalories2(Meal meal){

        List<String> ingredients = meal.ingredients;
        List<String> measures = meal.measures;
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

//    private Activity getActivity(){
//        ActivityManager am = (ActivityManager)getApplication().getSystemService(Context.ACTIVITY_SERVICE);
//        return am.;
//    }
}
