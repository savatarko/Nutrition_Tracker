package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.AppModule;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.ListMealsActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.helper.HelperFunctions;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealForPlan;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealName;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlan;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlanViewModel;

public class MealPlanFragment extends Fragment {


    MealName[] meals = {MealName.BREAKFAST, MealName.LUNCH, MealName.DINNER, MealName.SNACK};

    MealPlanViewModel viewModel;

    public MealPlanFragment() {
        // Required empty public constructor
    }

    List<Map<MealName,LinearLayout>> mealsLayouts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "onCreate: STATS");

        if (AppModule.getInstance().getMealPlanViewModel() != null) {
            viewModel = AppModule.getInstance().getMealPlanViewModel();
            MealPlan mealPlan = viewModel.getMealPlan().getValue();
            for (int i = 0; i < 7; i++) {
                for (MealName mealName : MealName.values()) {
                    MealForPlan meal = mealPlan.getMeal(i, mealName);
                    if (meal != null) {
                        LinearLayout layout = mealsLayouts.get(i).get(mealName);
                        generateViewForChosenMeal(layout, meal);
                    }
                }
            }
        }
        else {
            viewModel = new MealPlanViewModel();
            AppModule.getInstance().setMealPlanViewModel(viewModel);
        }
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
        mealsLayouts = new ArrayList<>();
        for(int i=1;i<8;i++) {
            mealsLayouts.add(new HashMap<>());
            generateViewForDay(layout, i);
        }


        EditText etEmail = new EditText(getContext());
        etEmail.setHint("Enter email");
        etEmail.setPadding(10, 30, 10, 20);

        Button btnSend = new Button(getContext());
        btnSend.setText(R.string.send);
        btnSend.setOnClickListener(v->{
            String email = etEmail.getText().toString();
            /*if(!HelperFunctions.isValidEmail(email)) {
                etEmail.setError("Invalid email");
                return;
            }*/
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Meal plan");
            intent.putExtra(Intent.EXTRA_TEXT, "Meal plan for this week");
            startActivity(Intent.createChooser(intent, "Send Email"));
        });



        layout.addView(etEmail);
        layout.addView(btnSend);

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
        dayLayout.setWeightSum(4);
        for(MealName meal : meals) {
            generateViewForMeal(dayLayout, meal, day-1);
        }


        layout.addView(dayLayout);
    }

    private void generateViewForMeal(LinearLayout parent, MealName meal, int day) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        params.width = 0;
        layout.setLayoutParams(params);

        TextView textView = new TextView(getContext());
        textView.setText(meal.toString());
        layout.addView(textView);

        LinearLayout holder = new LinearLayout(getContext());
        holder.setOrientation(LinearLayout.VERTICAL);
        mealsLayouts.get(day).put(meal, holder);

        MealForPlan mealForPlan = viewModel.getMealPlan().getValue().getMeal(day, meal);
        if(mealForPlan!=null) {
            generateViewForChosenMeal(holder, mealForPlan);
        } else {

            AppCompatButton button = new AppCompatButton(getContext());
            button.setText("Add");
            button.setOnClickListener(v -> {

                viewModel.setMealName(meal);
                viewModel.setDay(day);

                Intent intent = new Intent(getContext(), ListMealsActivity.class);
                //intent.putExtra("category", category.getName());
                intent.putExtra("filter", new MealFilter());
                intent.putExtra("addMeal", true);
                getContext().startActivity(intent);
            });
            holder.addView(button);
        }
        layout.addView(holder);
        parent.addView(layout);
    }

    private void generateViewForChosenMeal(LinearLayout parent, MealForPlan meal){

        parent.removeAllViews();
        TextView textView = new TextView(getContext());
        textView.setText(meal.getName());
        parent.addView(textView);
        ImageView imageView = new ImageView(getContext());
        imageView.setAdjustViewBounds(true);
        imageView.setMaxWidth(parent.getWidth());
        imageView.setMaxHeight(parent.getWidth());
        File imgFile = new File(meal.getImage());
        if(imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
        else {
            GetImages task = new GetImages(meal.getImage(), imageView,"meal");
            task.execute(meal.getImage());
        }
        //HelperFunctions.loadImage(imageView, meal.getImage());
        /*new Thread(() -> {
            try {
                File imgFile = new File(meal.getImage());
                if(imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                }
                else {
                    HttpURLConnection con = (HttpURLConnection) new URL(meal.getImage()).openConnection();
                    con.connect();
                    InputStream input = con.getInputStream();
                    final Bitmap bm = BitmapFactory.decodeStream(input);
                    //imageView.setImageBitmap(bm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();*/
        TextView textView1 = new TextView(getContext());
        textView1.setText(meal.getImage());
        //parent.addView(textView1);
        parent.addView(imageView);


    }

    @Override
    public void onResume() {
        super.onResume();
        MealForPlan mealForPlan = viewModel.getMealPlan().getValue().getMeal(viewModel.getDay(), viewModel.getMealName());
        Log.d("TEST", "onResume: " + mealForPlan);
        //this.onViewCreated(getView(), null);

        if(mealForPlan!=null) {
            Log.d("TEST", "onResume: USO");
            LinearLayout layout = mealsLayouts.get(viewModel.getDay()).get(viewModel.getMealName());
            generateViewForChosenMeal(layout, mealForPlan);
        }
    }


    private class GetImages extends AsyncTask<Object, Object, Object>
    {
        private String requestUrl, imagename_;
        private ImageView view;
        private Bitmap bitmap;
        private FileOutputStream fos;

        private GetImages(String requestUrl, ImageView view, String _imagename_)
        {
            this.requestUrl = requestUrl;
            this.view = view;
            this.imagename_ = _imagename_;
        }

        @Override
        protected Object doInBackground(Object... objects)
        {
            try
            {
                URL url = new URL(requestUrl);
                URLConnection conn = url.openConnection();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o)
        {
            view.setImageBitmap(bitmap);
        }
    }
}