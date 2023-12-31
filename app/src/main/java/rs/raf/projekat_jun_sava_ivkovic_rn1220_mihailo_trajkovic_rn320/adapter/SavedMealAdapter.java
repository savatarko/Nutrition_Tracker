package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.AppModule;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.ChangeSavedMealActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.MealDetailsActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.helper.HelperFunctions;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealForPlan;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlanViewModel;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealViewModel;

public class SavedMealAdapter extends RecyclerView.Adapter<SavedMealAdapter.SavedMealViewHolder>{

    private List<SavedMeal> meals = new ArrayList<>();

    private boolean addMeal = false;
    private Activity activity;

    public SavedMealAdapter(boolean addMeal, Activity activity) {
        super();
        this.addMeal = addMeal;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SavedMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new SavedMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedMealViewHolder holder, int position) {
        SavedMeal meal = meals.get(position);
        holder.name.setText(meal.name);
        //holder.img.setImageBitmap(meal.img);
        String cal = String.format("%.0f",meal.calories)+ " kcal";
        holder.calories.setText(cal);
        holder.date.setText(HelperFunctions.formatDate(meal.date));
        holder.mealType.setText(meal.mealType);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File imgFile = new File(meal.image);
                    if(imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        holder.img.setImageBitmap(myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        holder.frameLayout.setOnClickListener(e->{
            if(addMeal)
            {
                AppModule.getInstance().getMealPlanViewModel().addMeal(meal);
                activity.finish();
            }
            else {
                Intent intent = new Intent(holder.itemView.getContext(), ChangeSavedMealActivity.class);
                intent.putExtra("mealid", meal.id);
                intent.putExtra("meal", (new Gson()).toJson(meal));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public void setMeals(List<SavedMeal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setMealsNoNotif(List<SavedMeal> meals) {
        this.meals = meals;
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }



     class SavedMealViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView img;
        private FrameLayout frameLayout;
        private TextView mealType;
        private TextView date;
        private TextView calories;


        public SavedMealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView2);
            img = itemView.findViewById(R.id.imageView);
            frameLayout = itemView.findViewById(R.id.MealItem);
            mealType = itemView.findViewById(R.id.textView4);
            date = itemView.findViewById(R.id.textView5);
            calories = itemView.findViewById(R.id.textView6);
        }
    }
}
