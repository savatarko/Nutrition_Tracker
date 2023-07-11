package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.ListMealsActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories = new ArrayList<>();

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        holder.button.setOnClickListener(e->{
            /*
            categories.remove(category);
            Log.d("test123", "test");
            setCategories(categories);

             */
            AlertDialog.Builder test = new AlertDialog.Builder(holder.itemView.getContext());
            test.setTitle(category.getName());
            test.setMessage(category.getDesc());
            test.setPositiveButton(R.string.ok, (dialog, which) -> {
                dialog.dismiss();
            });
            test.show();
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(category.getUrl()).openConnection();
                    con.connect();
                    InputStream input = con.getInputStream();
                    final Bitmap bm = BitmapFactory.decodeStream(input);

                    holder.itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            Drawable d = new BitmapDrawable(holder.itemView.getResources(), bm);
                            holder.itemView.setBackgroundDrawable(d);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        holder.frame.setOnClickListener(e->{
            Intent intent = new Intent(holder.itemView.getContext(), ListMealsActivity.class);
            //intent.putExtra("category", category.getName());
            MealFilter mealFilter = new MealFilter();
            mealFilter.setCategory(category.getName());
            mealFilter.setFromHome(true);
            intent.putExtra("filter", mealFilter);
            holder.itemView.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private Button button;
        private Image image;
        private FrameLayout frame;
        public CategoryViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textView3);
            button = itemView.findViewById(R.id.button);
            frame = itemView.findViewById(R.id.CategoryItem);
        }
    }
}
