package com.example.onlineshopingapp.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.OrderProductAdapter;
import com.example.onlineshopingapp.Adapter.ProductAdapter;
import com.example.onlineshopingapp.Fragment.CartFragment;
import com.example.onlineshopingapp.Model.OrderModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Model.ProfileModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    ImageView imgCallback;
    TextView tvProfile,tvAddress,tvTotal,tvCheckout;
    ArrayList<ProductModel> list;
    RadioButton rbCash,rbMomo;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        tvProfile = findViewById(R.id.tvProfile);
        tvAddress = findViewById(R.id.tvAddress);
        gridView = findViewById(R.id.gridView);
        tvTotal = findViewById(R.id.tvTotal);
        tvCheckout = findViewById(R.id.tvCheckout);
        rbCash = findViewById(R.id.rbCash);
        rbMomo = findViewById(R.id.rbMomo);
        rbCash.setChecked(true);
        transitionScreen();

        SharedPreferences sharedPref = getSharedPreferences("userfile", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");

        getProfile(userId);

        list = new CartFragment().listSelect;
        total();

        OrderProductAdapter adapter = new OrderProductAdapter(this,list);
        gridView.setAdapter(adapter);

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout(userId,list);
            }
        });
    }

    private void checkout(String userId,ArrayList list){
        if(rbCash.isChecked()){
            order(userId,list);
        }else if(rbMomo.isChecked()){
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
        }
    }


    private void order(String userId,ArrayList list){
        OrderModel objOrder = new OrderModel();
        objOrder.setUserId(userId);
        objOrder.setProduct(list);
        String address = tvProfile.getText().toString()+" | "+tvAddress.getText().toString();
        objOrder.setAddress(address);

        ApiService.apiService.addOrder(objOrder).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String noti = response.body();
                    Toast.makeText(PayActivity.this, noti, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(),OrderActivity.class));
                    finish();
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PayActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setData(ProfileModel model){
        if(model!=null){
            tvProfile.setText(model.getName()+" | "+model.getPhone());
            tvAddress.setText(model.getAddress());
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
                Toast.makeText(PayActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void total() {
        int sum = 0;
        for (ProductModel model : list){
            sum+= model.getPrice()*model.getQuantity();
        }
        tvTotal.setText(sum+" VNĐ");
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