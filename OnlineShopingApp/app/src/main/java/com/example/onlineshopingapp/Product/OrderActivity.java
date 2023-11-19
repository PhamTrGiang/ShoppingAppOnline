package com.example.onlineshopingapp.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.OrderAdapter;
import com.example.onlineshopingapp.Model.OrderModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ImageView imgCallback;
    OrderAdapter adapter;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        transitionScreen();
        gridView = findViewById(R.id.gridView);

        adapter = new OrderAdapter(new ArrayList<>(),this,0);
        gridView.setAdapter(adapter);

        SharedPreferences sharedPref = getSharedPreferences("userfile", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");
        loadData(userId);
    }

    private void setDataAdapter(ArrayList arr){
        if (arr != null) {
            adapter.setTableItems(arr);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadData(String userId){
        ApiService.apiService.getOrder(userId).enqueue(new Callback<ArrayList<OrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<OrderModel> list = response.body();
                    setDataAdapter(list);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
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