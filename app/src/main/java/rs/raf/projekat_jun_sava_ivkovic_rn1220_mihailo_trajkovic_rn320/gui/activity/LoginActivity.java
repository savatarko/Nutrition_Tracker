package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.AppDatabase;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.database.user.User;

public class LoginActivity extends AppCompatActivity {

    private Button loginbt;
    private Button registerbt;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        initView();
        initListeners();
    }
    private void initView(){
        loginbt = findViewById(R.id.buttonLogin);
        registerbt = findViewById(R.id.buttonRegister);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
    }

    private void initListeners(){
        loginbt.setOnClickListener(e->{
            if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), R.string.empty_field, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            String pass = password.getText().toString();
            if(pass.length()<4 || pass.contains("~") || pass.contains("#") || pass.contains("$") || pass.contains("%") || pass.contains("^") || pass.contains("&") || pass.contains("*")){
                Toast toast = Toast.makeText(getApplicationContext(), R.string.wrong_pass_format, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            int flag = 0;
            for(int i = 0; i < pass.length(); i++){
                if(pass.charAt(i) <='9' && pass.charAt(i) >= '0'){
                    flag |= 1;
                }
                if(pass.charAt(i) <='Z' && pass.charAt(i) >= 'A'){
                    flag |= 2;
                }
            }
            if(flag != 3){
                Toast toast = Toast.makeText(getApplicationContext(), R.string.wrong_pass_format, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if(checkDatabase()){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkDatabase(){
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        User user = db.userDao().findUser(username.getText().toString(), password.getText().toString());
        if(user == null){
            Toast toast = Toast.makeText(getApplicationContext(), R.string.account_not_found, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putInt("login", user.id)
                .apply();
        //db.close();
        return true;
    }
}