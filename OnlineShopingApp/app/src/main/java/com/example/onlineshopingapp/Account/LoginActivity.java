package com.example.onlineshopingapp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.MainActivity;
import com.example.onlineshopingapp.Model.UserModel;
import com.example.onlineshopingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgCallback;
    TextView tvTransition;
    EditText edEmail,edPassword;
    Button btnLogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        transitionScreen();
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String email = edEmail.getText().toString();
        String pass = edPassword.getText().toString();
        if (email.length()==0||pass.length()==0){
            Toast.makeText(this, "Account is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        login(email,pass);
    }

    private void login(String email,String password){
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);

        ApiService.apiService.setLogin(userModel).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String userId = response.body();
                    Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPref = getSharedPreferences("userfile",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userId",userId).apply();

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } else{
                    Toast.makeText(LoginActivity.this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void transitionScreen(){
        imgCallback = findViewById(R.id.imgCallback);
        tvTransition = findViewById(R.id.tvTransition);
        tvTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        imgCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}