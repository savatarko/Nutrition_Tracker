package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.FilterFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.MainScreenFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.MealPlanFragment;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment.StatsFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String text = tab.getText().toString();
                if (text.equalsIgnoreCase("home")) {
                    MainScreenFragment mainScreenFragment = (MainScreenFragment) getSupportFragmentManager().findFragmentByTag("mainScreenFragment");
                    if (mainScreenFragment == null || !mainScreenFragment.isVisible()) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView2, new MainScreenFragment(), "mainScreenFragment");
                        transaction.commit();
                    }
                } else if (text.equalsIgnoreCase("filter")) {
                    FilterFragment filterFragment = (FilterFragment) getSupportFragmentManager().findFragmentByTag("filterFragment");
                    if (filterFragment == null || !filterFragment.isVisible()) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView2, new FilterFragment(), "filterFragment");
                        transaction.commit();
                    }
                } else if (text.equalsIgnoreCase("stats")) {
                    StatsFragment statsFragment = (StatsFragment) getSupportFragmentManager().findFragmentByTag("statsFragment");
                    if (statsFragment == null || !statsFragment.isVisible()) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView2, new StatsFragment(), "statsFragment");
                        transaction.commit();
                    }
                }
                else if (text.equalsIgnoreCase("meal plan")) {
                    MealPlanFragment mealPlanFragment = (MealPlanFragment) getSupportFragmentManager().findFragmentByTag("mealPlanFragment");
                    if (mealPlanFragment == null || !mealPlanFragment.isVisible()) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView2, new MealPlanFragment(), "mealPlanFragment");
                        transaction.commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}