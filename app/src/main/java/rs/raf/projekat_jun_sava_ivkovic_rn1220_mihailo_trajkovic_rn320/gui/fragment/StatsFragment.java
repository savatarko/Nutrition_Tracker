package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user.User;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.helper.HelperFunctions;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealDetailsViewModel;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MyViewModelFactory;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.SavedMealViewModel;

public class StatsFragment extends Fragment {


    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;
    BarDataSet barDataSet2;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    ArrayList barEntriesArrayList2;

    SavedMealViewModel savedMealViewModel;

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedMealViewModel = new ViewModelProvider(this).get(SavedMealViewModel.class);

        barChart = view.findViewById(R.id.idBarChart);

        view.findViewById(R.id.btnCalories)
                .setOnClickListener(v -> {
                    Log.d("TEST", "onViewCreated: btnCalories");
                    setChartForCalories();
                    barChart.invalidate();
                });

        view.findViewById(R.id.btnMealsNumber)
                .setOnClickListener(v -> {
                    setChartForMealNumber();
                    barChart.invalidate();
                });


        setChartForMealNumber();

        barChart.getDescription().setEnabled(false);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MMM");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return LocalDateTime.now().minus((long) (6-value), ChronoUnit.DAYS).format(dtf);
            }
        });


    }

    private void setChartForMealNumber(){

        getBarEntriesForMealNumber();
        barDataSet = new BarDataSet(barEntriesArrayList, getContext().getString(R.string.meals_number));

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);
        barChart.getLegend().setEnabled(false);


        // adding color to our bar data set.
        barDataSet.setColors(getResources().getColor(R.color.colorPrimaryDark));

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);

        for(YAxis yAxis : new YAxis[]{barChart.getAxisLeft(), barChart.getAxisRight()}){
//            yAxis.setDrawGridLines(false);
            yAxis.setDrawZeroLine(true);
            yAxis.setAxisMaximum(5f);
            yAxis.setAxisMinimum(0f);
            yAxis.setGranularity(1f);
        }
    }

    private void setChartForCalories(){
        getBarEntriesForCalories();
        barDataSet = new BarDataSet(barEntriesArrayList, getContext().getString(R.string.calories));
        barDataSet2 = new BarDataSet(barEntriesArrayList2, null);

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet, barDataSet2);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);
        barChart.getLegend().setEnabled(false);

        // adding color to our bar data set.
        barDataSet.setColors(getResources().getColor(R.color.green, null));
        barDataSet2.setColors(getResources().getColor(R.color.red, null));

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet2.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barDataSet2.setValueTextSize(16f);

        for(YAxis yAxis : new YAxis[]{barChart.getAxisLeft(), barChart.getAxisRight()}){
//            yAxis.setDrawGridLines(false);
            yAxis.setDrawZeroLine(true);
            yAxis.setAxisMaximum(5000f);
            yAxis.setAxisMinimum(0f);
            //yAxis.setGranularity(1f);
        }
    }

    private void getBarEntriesForMealNumber() {
        savedMealViewModel.getSavedMeals(null);
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();


        List<SavedMeal> savedMeals = savedMealViewModel.getMeals().getValue();
        for(int i=0;i<=6;i++) {
            LocalDateTime date = LocalDateTime.now().minus(i, ChronoUnit.DAYS); //.format(dtf);
            Calendar cal = Calendar.getInstance();
            cal.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
            barEntriesArrayList.add(new BarEntry((float) 6-i, getMealsForDate(savedMeals, HelperFunctions.trimDate(cal.getTime()))));
        }
    }

    private void getBarEntriesForCalories() {

        savedMealViewModel.getSavedMeals(null);
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
        barEntriesArrayList2 = new ArrayList<>();

        int limit = calculateDailyCaloriesLimit();
        Log.d("TEST", "getBarEntriesForCalories: limit: " + limit);

        List<SavedMeal> savedMeals = savedMealViewModel.getMeals().getValue();
        for(int i=0;i<=6;i++) {
            LocalDateTime date = LocalDateTime.now().minus(i, ChronoUnit.DAYS); //.format(dtf);
            Calendar cal = Calendar.getInstance();
            cal.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
            int calories =  getCaloriesForDate(savedMeals, HelperFunctions.trimDate(cal.getTime()));
            if(calories <= limit)
                barEntriesArrayList.add(new BarEntry((float) 6-i, calories));
            else
                barEntriesArrayList2.add(new BarEntry((float) 6-i, calories));
        }
    }

    private int getMealsForDate(List<SavedMeal> meals, Date date){
        return meals.stream().filter(meal -> meal.date.equals(date)).collect(Collectors.toList()).size();
    }


    private int getCaloriesForDate(List<SavedMeal> meals, Date date){
        return meals.stream().filter(meal -> meal.date.equals(date)).mapToInt(meal -> (int) meal.calories).sum();
    }

    private int calculateDailyCaloriesLimit(){


        Activity activity = getActivity();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("login", -1);
        AppDatabase db = AppDatabase.getInstance(activity.getApplicationContext());
        User user = db.userDao().findUserById(id);
        return user.dailyCaloriesLimit();
    }

}