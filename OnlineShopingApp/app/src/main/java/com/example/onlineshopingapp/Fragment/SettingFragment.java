package com.example.onlineshopingapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Account.LoginActivity;
import com.example.onlineshopingapp.Account.ProfileActivity;
import com.example.onlineshopingapp.Account.RegisterActivity;
import com.example.onlineshopingapp.MainActivity;
import com.example.onlineshopingapp.ManageScreen.ManageActivity;
import com.example.onlineshopingapp.Model.ProfileModel;
import com.example.onlineshopingapp.Product.OrderActivity;
import com.example.onlineshopingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingFragment extends Fragment {
    LinearLayout lnProfile,lnChangepass,lnManager,lnLogout,lnOrder;
    Button btnLogin,btnRegister;
    TextView tvFullname;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;

        SharedPreferences sharedPref = getActivity().getSharedPreferences("userfile",Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");

        if(!userId.equals("")){
            v = inflater.inflate(R.layout.fragment_setting, container, false);
            lnLogout = v.findViewById(R.id.lnLogout);
            lnProfile = v.findViewById(R.id.lnProfile);
            lnChangepass = v.findViewById(R.id.lnChangepass);
            lnManager = v.findViewById(R.id.lnManager);
            lnOrder = v.findViewById(R.id.lnOrder);
            tvFullname = v.findViewById(R.id.tvFullname);

            getProfile(userId);
            checkManager(userId);
            lnManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Managerment();
                }
            });
            lnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });
            lnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profile();
                }
            });
            lnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    order();
                }
            });
        }else{
            v = inflater.inflate(R.layout.fragment_setting_nologin, container, false);
            btnLogin = v.findViewById(R.id.btnLogin);
            btnRegister = v.findViewById(R.id.btnRegister);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    register();
                }
            });
        }

        return v;
    }

    private void getProfile(String id){
        ApiService.apiService.getProfile(id).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel objProfile = response.body();
                    if (objProfile!=null){
                        tvFullname.setText(objProfile.getName());
                    }
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }
            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkManager(String id){
        ApiService.apiService.getStatus(id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int status = response.body();
                    if(status==0){
                        lnManager.setVisibility(View.GONE);
                    }else{
                        lnManager.setVisibility(View.VISIBLE);
                    }

                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
    private void register(){
        Intent i = new Intent(getContext(), RegisterActivity.class);
        startActivity(i);
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout");
        builder.setMessage("Log out of your account?");
        builder.setCancelable(true);
        builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("userfile",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear().apply();

                getActivity().recreate();

                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
    private void profile(){
        Intent i = new Intent(getContext(), ProfileActivity.class);
        startActivity(i);
    }

    private void order(){
        startActivity(new Intent(getContext(), OrderActivity.class));
    }

    private void changePass(){

    }
    private void Managerment(){
        Intent i = new Intent(getContext(), ManageActivity.class);
        startActivity(i);
    }
}