package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.AppModule;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user.User;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.databinding.FragmentProfileBinding;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.LoginActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.ProfileViewModel;

public class ProfileFragment extends Fragment {


    private FragmentProfileBinding binding;

    private ProfileViewModel profileViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Activity activity = getActivity();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("login", -1);
        profileViewModel = new ProfileViewModel(id, activity);

        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.setViewmodel(profileViewModel);

        binding.btnSaveChanges.setOnClickListener(v -> {
            profileViewModel.updateUser();
            Snackbar.make(binding.getRoot(), "User info updated", Snackbar.LENGTH_SHORT)
                    .setAction(null, null).show();  });

        binding.btnLogout.setOnClickListener(v -> {
            sharedPreferences
                    .edit()
                    .remove("login")
                    .apply();
            Intent mainIntent = new Intent(activity, LoginActivity.class);
            this.startActivity(mainIntent);
            activity.finish();

        });


        View root = binding.getRoot();
        return root;
    }

}