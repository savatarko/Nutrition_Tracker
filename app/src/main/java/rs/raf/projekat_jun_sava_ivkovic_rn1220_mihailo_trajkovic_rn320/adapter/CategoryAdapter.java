package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.category.Category;

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
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        HttpURLConnection con = (HttpURLConnection) new URL(category.getUrl()).openConnection();
                        con.connect();
                        InputStream input = con.getInputStream();
                        Bitmap bm = BitmapFactory.decodeStream(input);
                        Drawable d = new BitmapDrawable(bm);
                        holder.itemView.setBackgroundDrawable(d);


                        /*
                        Bitmap img = BitmapFactory.decodeStream(getClass().getResourceAsStream(category.getUrl()));
                        BitmapDrawable bg = new BitmapDrawable(img);
                        holder.button.setBackgroundDrawable(bg);


                         */
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        //holder.itemView.setBackgroundDrawable(bg);
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
        public CategoryViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textView3);
            button = itemView.findViewById(R.id.button);
        }
    }
}
