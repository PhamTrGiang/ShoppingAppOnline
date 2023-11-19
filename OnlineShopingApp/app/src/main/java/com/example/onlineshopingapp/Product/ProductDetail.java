package com.example.onlineshopingapp.Product;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Account.LoginActivity;
import com.example.onlineshopingapp.Account.RegisterActivity;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.Model.CartModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends AppCompatActivity {
    ImageView imgView,imgCallback;
    TextView tvName,tvPrice,tvQuantity,tvBrand;
    LinearLayout lnAddtoCart,lnBuyNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        imgView = findViewById(R.id.imgView);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvBrand = findViewById(R.id.tvBrand);
        lnAddtoCart = findViewById(R.id.lnAddtoCart);
        lnBuyNow = findViewById(R.id.lnBuyNow);

        setImgCallback();

        Intent i = getIntent();
        String id= i.getStringExtra("id");
        if(id.equals("")){
            Toast.makeText(this, "Have a problem", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }{
            loadData(id);
        }
        lnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(id,1);
            }
        });
        lnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyNow();
            }
        });

    }

    private void loadData(String id){
        ApiService.apiService.getProductDetail(id).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if(response.isSuccessful()){
                    ProductModel objProduct = response.body();
                    tvName.setText(objProduct.getName());
                    tvPrice.setText(objProduct.getPrice()+" VNĐ");
                    tvBrand.setText(objProduct.getBrand());
                    tvQuantity.setText("Quantity: "+objProduct.getQuantity());


                    Picasso.with(ProductDetail.this)
                            .load(objProduct.getImage())
                            .placeholder(R.drawable.baseline_image_24)
                            .error(R.drawable.baseline_hide_image_24)
                            .fit()
                            .into(imgView);
                }else{
                    Toast.makeText(ProductDetail.this, "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(ProductDetail.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addToCart(String id,int quantity){
        SharedPreferences sharedPref = getSharedPreferences("userfile",MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");
        if (userId.equals("")){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }else{
            CartModel objCart = new CartModel();
            objCart.setUserId(userId);
            objCart.setProductId(id);
            objCart.setQuantity(quantity);

            ApiService.apiService.addCart(objCart).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Add to cart successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("MAIN", "Respone Fail" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void buyNow(){

    }

    private void setImgCallback(){
        imgCallback = findViewById(R.id.imgCallback);
        imgCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void deleteProduct(String id){
        Call<ArrayList<ProductModel>> call = ApiService.apiService.deleteProduct(id);
        call.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetail.this, "Delete vehicle successful", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(ProductDetail.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage(R.string.noti_delete);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct(id);
                finish();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
}