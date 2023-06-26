package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int mParam;


    public MyViewModelFactory(Application application, int param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MealDetailsViewModel(mApplication, mParam);
    }
}
