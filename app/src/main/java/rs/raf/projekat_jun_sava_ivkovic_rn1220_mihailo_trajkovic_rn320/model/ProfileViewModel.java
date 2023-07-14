package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user.User;

public class ProfileViewModel {

    MutableLiveData<String> username = new MutableLiveData<>();
    MutableLiveData<Integer> height = new MutableLiveData<>();
    MutableLiveData<Integer> weight = new MutableLiveData<>();
    MutableLiveData<Integer> age = new MutableLiveData<>();
    MutableLiveData<String> sex = new MutableLiveData<>();
    MutableLiveData<Float> physicalActivity = new MutableLiveData<>();
    private int id;
    private Activity activity;

    public ProfileViewModel(int id, Activity activity) {

        this.id = id;
        this.activity = activity;
        AppDatabase db = AppDatabase.getInstance(activity.getApplicationContext());
        User user = db.userDao().findUserById(id);
        username.setValue(user.username);
        height.setValue(user.height);
        weight.setValue(user.weight);
        age.setValue(user.age);
        sex.setValue(user.sex);
        physicalActivity.setValue(user.physicalActivity);

        Log.d("TEST", "ProfileViewModel: "+user.toString());

    }

    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public String getHeight() {
        return height.getValue().toString();
    }

    public void setHeight(String height) {
        this.height.setValue(Integer.parseInt(height));
    }

    public String getWeight() {
        return weight.getValue().toString();
    }

    public void setWeight(String weight) {
        try {
            this.weight.setValue(Integer.parseInt(weight));
        }catch (Exception e){
            this.weight.setValue(0);
        }
    }

    public String getAge() {
        return age.getValue().toString();
    }

    public void setAge(String age) {
        try {
            this.age.setValue(Integer.parseInt(age));
        }
        catch (Exception e){
            this.age.setValue(20);
        }
    }

    public void updateUser(){


        AppDatabase db = AppDatabase.getInstance(activity.getApplicationContext());
        User user = db.userDao().findUserById(id);
        user.age = this.age.getValue();
        user.height = this.height.getValue();
        user.weight = this.weight.getValue();
        db.userDao().updateUsers(user);
    }

}
