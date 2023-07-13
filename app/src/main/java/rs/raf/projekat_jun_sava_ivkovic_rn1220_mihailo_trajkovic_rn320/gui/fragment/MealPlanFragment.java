package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.AppModule;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.ListMealsActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealName;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlanViewModel;

public class MealPlanFragment extends Fragment {


    MealName[] meals = {MealName.BREAKFAST, MealName.LUNCH, MealName.DINNER, MealName.SNACK};

    MealPlanViewModel viewModel;

    public MealPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new MealPlanViewModel();
        AppModule.getInstance().setMealPlanViewModel(viewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.mealPlanLayout);
        TextView textView = new TextView(getContext());
        textView.setText("Hello World");
        layout.addView(textView);
        for(int i=1;i<8;i++) {
            generateViewForDay(layout, i);
        }
    }

    private void generateViewForDay(LinearLayout layout, int day) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        TextView textView = new TextView(getContext());
        textView.setText(symbols.getWeekdays()[(day%7)+1]);
        layout.addView(textView);

        LinearLayout dayLayout = new LinearLayout(getContext());
        dayLayout.setOrientation(LinearLayout.HORIZONTAL);
        dayLayout.setPadding(0, 0, 0, 20);
        dayLayout.setElevation(10);
        for(MealName meal : meals) {
            generateViewForMeal(dayLayout, meal, day-1);
        }



        layout.addView(dayLayout);
    }

    private void generateViewForMeal(LinearLayout parent, MealName meal, int day) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(getContext());
        textView.setText(meal.toString());
        layout.addView(textView);
        AppCompatButton button = new AppCompatButton(getContext());
        button.setText("Add");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.setMealName(meal);
                viewModel.setDay(day);

                Intent intent = new Intent(getContext(), ListMealsActivity.class);
                //intent.putExtra("category", category.getName());
                intent.putExtra("addMeal", true);
                getContext().startActivity(intent);
            }
        });
        layout.addView(button);

        parent.addView(layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TEST", "onResume: " + viewModel.getMealPlan().getValue().getMeal(viewModel.getDay(), viewModel.getMealName()));
    }
}