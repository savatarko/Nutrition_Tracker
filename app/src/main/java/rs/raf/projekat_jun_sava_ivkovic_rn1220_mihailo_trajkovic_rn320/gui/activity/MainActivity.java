package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;



import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.AppModule;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.databinding.ActivityMainBinding;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.FilterFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.MainScreenFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.MealPlanFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.StatsFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlan;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealPlanViewModel;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    NavController navController;

    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST", "onCreate: MAIN ACTIVITY");
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String dataString = intent.getDataString();
        if(dataString != null) {
            dataString = dataString.replace("https://www.nutrition-tracker.rs/mealplan?data=", "");
            Log.d("TEST", "onCreate: DATA " + dataString);
            if (!dataString.equals("")) {
                String decoded = "";
                try {
                    decoded = URLDecoder.decode(dataString, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                Log.d("TEST", "onCreate: DECODED " + decoded);
                MealPlanViewModel mealPlanViewModel = new MealPlanViewModel();
                mealPlanViewModel.setMealPlan((new Gson()).fromJson(decoded, MealPlan.class));
                AppModule.getInstance().setMealPlanViewModel(mealPlanViewModel);
                intent.setData(null);
                flag = true;
//                navController.navigate(R.id.meal);
            }
        }
        initNavigation();

    }

    private void initNavigation() {

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.bottomNavigation);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_filter, R.id.navigation_stats, R.id.navigation_meal_plan, R.id.navigation_profile)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

//        if (flag) {
//            NavOptions navOptions = new NavOptions.Builder()
//                    .setPopUpTo(R.id.navigation_home, true)
//                    .build();
//            flag = false;
//            navController.navigate(R.id.navigation_meal_plan, null, navOptions);
//        }
    }
}