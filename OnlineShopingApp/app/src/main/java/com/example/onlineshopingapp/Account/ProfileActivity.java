package com.example.onlineshopingapp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Model.ProfileModel;
import com.example.onlineshopingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView imgCallback;
    EditText edName,edPhone,edAddress;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        transitionScreen();
        edName = findViewById(R.id.edName);
        edPhone = findViewById(R.id.edPhone);
        edAddress = findViewById(R.id.edAddress);
        btnSave =findViewById(R.id.btnSave);
        SharedPreferences sharedPref = getSharedPreferences("userfile", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");
        getProfile(userId);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile(userId);
            }
        });

    }
    private void setData(ProfileModel model){
        if(model!=null){
        edName.setText(model.getName());
        edPhone.setText(model.getPhone());
        edAddress.setText(model.getAddress());
        }
    }
    private void getProfile(String id){
        ApiService.apiService.getProfile(id).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel objProfile = response.body();
                    setData(objProfile);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }
            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setProfile(String id){
        ProfileModel model = new ProfileModel();
        model.setName(edName.getText().toString());
        model.setPhone(edPhone.getText().toString());
        model.setAddress(edAddress.getText().toString());
        model.setUserId(id);

        ApiService.apiService.setProfile(model).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel objProfile = response.body();
                    setData(objProfile);
                    Toast.makeText(ProfileActivity.this, "Update profile is success", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void transitionScreen(){
        imgCallback = findViewById(R.id.imgCallback);
        imgCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}