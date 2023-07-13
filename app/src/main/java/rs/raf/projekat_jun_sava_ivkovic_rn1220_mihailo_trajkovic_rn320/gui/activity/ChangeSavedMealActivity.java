package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.meals.Meal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.savedmeal.SavedMeal;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.helper.HelperFunctions;

public class ChangeSavedMealActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button breakfastbt;
    private Button lunchbt;
    private Button dinnerbt;
    private Button snackbt;
    private Button savebt;
    private Button deletebt;
    private TextView nametv;
    private ImageView image;
    private int id;
    int selected = 1;
    Date date = new Date();
    private int pic_id;
    private Map<Integer, String> typemap = new HashMap<>();
    private boolean haschangedimg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_saved_meal);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton1);
        dateButton.setText(getTodaysDate());
        id = getIntent().getIntExtra("mealid",0);

        typemap.put(1,"breakfast");
        typemap.put(2,"lunch");
        typemap.put(3,"dinner");
        typemap.put(4,"snack");

        initView();
        initData();
    }
    private void initView(){
        breakfastbt = findViewById(R.id.button41);
        lunchbt = findViewById(R.id.button61);
        dinnerbt = findViewById(R.id.button71);
        snackbt = findViewById(R.id.button81);
        savebt = findViewById(R.id.button91);
        deletebt = findViewById(R.id.button101);
        nametv = findViewById(R.id.savemealname1);
        image = findViewById(R.id.imageView31);

        breakfastbt.setBackgroundResource(R.color.enabled);
        lunchbt.setBackgroundResource(R.color.disabled);
        dinnerbt.setBackgroundResource(R.color.disabled);
        snackbt.setBackgroundResource(R.color.disabled);
    }

    private void initData(){
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        //Meal meal = (new Gson()).fromJson(getIntent().getExtras().getString("meal"), Meal.class);
        int id = getIntent().getIntExtra("mealid",0);
        if(id == 0){
            return;
        }
        SavedMeal meal = db.savedMealDao().getById(id);
        nametv.setText(meal.name);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File imgFile = new File(meal.image);
                    if(imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        image.setImageBitmap(myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        breakfastbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 1;
                breakfastbt.getBackground().setTint(getResources().getColor(R.color.enabled));
                lunchbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                dinnerbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                snackbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            }
        });
        lunchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 2;
                breakfastbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                lunchbt.getBackground().setTint(getResources().getColor(R.color.enabled));
                dinnerbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                snackbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            }
        });
        dinnerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 3;
                breakfastbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                lunchbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                dinnerbt.getBackground().setTint(getResources().getColor(R.color.enabled));
                snackbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            }
        });
        snackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 4;
                breakfastbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                lunchbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                dinnerbt.getBackground().setTint(getResources().getColor(R.color.disabled));
                snackbt.getBackground().setTint(getResources().getColor(R.color.enabled));

            }
        });

        if(meal.mealType.equalsIgnoreCase("breakfast")){
            selected = 1;
            breakfastbt.getBackground().setTint(getResources().getColor(R.color.enabled));
            lunchbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            dinnerbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            snackbt.getBackground().setTint(getResources().getColor(R.color.disabled));
        }
        else if(meal.mealType.equalsIgnoreCase("lunch")){
            selected = 2;
            breakfastbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            lunchbt.getBackground().setTint(getResources().getColor(R.color.enabled));
            dinnerbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            snackbt.getBackground().setTint(getResources().getColor(R.color.disabled));
        }
        else if(meal.mealType.equalsIgnoreCase("dinner")){
            selected = 3;
            breakfastbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            lunchbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            dinnerbt.getBackground().setTint(getResources().getColor(R.color.enabled));
            snackbt.getBackground().setTint(getResources().getColor(R.color.disabled));
        }
        else{
            selected = 4;
            breakfastbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            lunchbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            dinnerbt.getBackground().setTint(getResources().getColor(R.color.disabled));
            snackbt.getBackground().setTint(getResources().getColor(R.color.enabled));
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy");

        dateButton.setText(meal.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(dtf));

        image.setOnClickListener(e->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.startcamera)
                    .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, pic_id);
                            haschangedimg = true;
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            builder.show();
        });

        savebt.setOnClickListener(e->{
            meal.date = HelperFunctions.trimDate(this.date);
            meal.mealType = typemap.get(selected);
            if(haschangedimg) {
                image.buildDrawingCache();
                meal.image = saveToInternalStorage(image.getDrawingCache(), id);
            }
            db.savedMealDao().deleteById(meal.id);
            db.savedMealDao().insert(meal);
            Toast.makeText(getApplicationContext(), "Meal updated", Toast.LENGTH_SHORT).show();
            finish();
        });

        deletebt.setOnClickListener(e->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            db.savedMealDao().deleteById(meal.id);
                            Toast.makeText(getApplicationContext(), "Meal deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.show();
        });
    }
    private String saveToInternalStorage(Bitmap bitmapImage, int id){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,id+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            image.setImageBitmap(photo);
        }
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            //month = month + 1;
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            date = cal.getTime();
            //String date = makeDateString(day, month, year);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy");
            dateButton.setText(dtf.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}