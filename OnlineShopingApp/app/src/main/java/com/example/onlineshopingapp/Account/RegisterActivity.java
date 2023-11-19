package com.example.onlineshopingapp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Model.UserModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgCallback;
    TextView tvTransition;
    EditText edEmail,edPassword,edRePassword;
    Button btnRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        transitionScreen();
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edRePassword = findViewById(R.id.edRepassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String email = edEmail.getText().toString();
        String pass = edPassword.getText().toString();
        String rePass = edRePassword.getText().toString();

        if (email.length()==0||pass.length()==0||rePass.length()==0){
            Toast.makeText(this, "Account is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length()<6){
            Toast.makeText(this, "Pass > 6", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!rePass.equals(pass)){
            Toast.makeText(this, "pass and repass incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        register(email,pass);
    }

    private void register(String email,String password){
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);

        ApiService.apiService.setRegister(userModel).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String userId = response.body();
                    Toast.makeText(RegisterActivity.this, "Register successfull", Toast.LENGTH_SHORT).show();


                    SharedPreferences sharedPref = getSharedPreferences("userfile",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userId",userId).apply();


                    finish();
                } else{
                    Toast.makeText(RegisterActivity.this, "da ton tai tai khoan", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
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