package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.MealDetailsActivity;

public class SavedMealAdapter extends RecyclerView.Adapter<SavedMealAdapter.SavedMealViewHolder>{

    private List<SavedMeal> meals = new ArrayList<>();

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


        //TODO: ovde treba da se popravi samo me mrzi sada
        holder.frameLayout.setOnClickListener(e->{
            Intent intent = new Intent(holder.itemView.getContext(), MealDetailsActivity.class);
            intent.putExtra("mealid", meal.id);
            intent.putExtra("meal", (new Gson()).toJson(meal));
            holder.itemView.getContext().startActivity(intent);
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


        public SavedMealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView2);
            img = itemView.findViewById(R.id.imageView);
            frameLayout = itemView.findViewById(R.id.MealItem);
            mealType = itemView.findViewById(R.id.textView4);
            date = itemView.findViewById(R.id.textView5);
        }
    }
}
