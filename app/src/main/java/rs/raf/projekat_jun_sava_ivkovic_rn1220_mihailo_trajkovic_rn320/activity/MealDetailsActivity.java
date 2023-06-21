package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;

public class MealDetailsActivity extends AppCompatActivity {

    private TextView mealName;
    private TextView mealCategory;
    private TextView mealArea;
    private TextView mealRecipe;
    private TextView video;
    private TextView mealIngredients;
    private TextView tags;
    private ImageView mealImage;
    private Button backbt;

    private int mealid;
    //private Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        mealid = getIntent().getExtras().getInt("mealid");
        //meal= (Meal) getIntent().getExtras().getSerializable("meal");
        initView();
    }
    private void initView(){
        mealName = findViewById(R.id.textView4);
        mealCategory = findViewById(R.id.textView5);
        mealArea = findViewById(R.id.textView6);
        mealRecipe = findViewById(R.id.textView7);
        video = findViewById(R.id.textView8);
        mealIngredients = findViewById(R.id.textView9);
        tags = findViewById(R.id.textView10);
        mealImage = findViewById(R.id.imageView2);
        backbt = findViewById(R.id.button5);

        AppDatabase db = AppDatabase.getInstance(this);
        Meal meal = db.mealDao().getById(mealid);
        mealName.setText(meal.name);
        mealCategory.setText(meal.category);
        mealArea.setText(meal.mealArea);
        mealRecipe.setText(meal.instructions);
        //video.setText(meal.);
        //mealIngredients.setText(meal.ingredients);
        tags.setText(meal.tags);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(meal.thumbnail).openConnection();
                    con.connect();
                    InputStream input = con.getInputStream();
                    final Bitmap bm = BitmapFactory.decodeStream(input);
                    mealImage.setImageBitmap(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        backbt.setOnClickListener(e->{
            finish();
        });
    }
}