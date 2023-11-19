package com.example.onlineshopingapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.CartAdapter;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.Model.CartModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Model.UserModel;
import com.example.onlineshopingapp.Product.PayActivity;
import com.example.onlineshopingapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.Callback{
    GridView gridView;
    TextView tvCheckout,tvTotal,tvTitle;
    CartAdapter adapter;
    CheckBox cbAll;
    public static ArrayList<ProductModel> listSelect;
    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        gridView = v.findViewById(R.id.gridView);
        cbAll = v.findViewById(R.id.cbAll);
        tvCheckout = v.findViewById(R.id.tvCheckout);
        tvTotal = v.findViewById(R.id.tvTotal);
        tvTitle = v.findViewById(R.id.tvTitle);

        listSelect = new ArrayList<ProductModel>();


        adapter = new CartAdapter(new ArrayList<>(),this,getContext());
        gridView.setAdapter(adapter);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("userfile", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");
        loadData(userId);

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOut();
            }
        });

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkAll();
            }
        });

        return v;
    }

    private void setDataAdapter(ArrayList arr){
        tvTitle.setText("Shopping Cart ("+arr.size()+")");
        if (arr != null) {
            adapter.setTableItems(arr);
            adapter.notifyDataSetChanged();
        }
    }
    private void loadData(String id){
        if(!id.equals("")){
            ApiService.apiService.getCart(id).enqueue(new Callback<ArrayList<CartModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CartModel>> call, Response<ArrayList<CartModel>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<CartModel> arrItem = response.body();
                        setDataAdapter(arrItem);
                    } else {
                        Toast.makeText(getContext(), "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CartModel>> call, Throwable t) {
                    Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    @Override
    public void edit(CartModel model) {

    }

    private void deleteCart(CartModel model){
        String id = model.get_id();
        ApiService.apiService.deleteCart(id,model.getUserId()).enqueue(new Callback<ArrayList<CartModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CartModel>> call, Response<ArrayList<CartModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Delete successful", Toast.LENGTH_SHORT).show();
                    ArrayList<CartModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CartModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void delete(CartModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage(R.string.noti_delete);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCart(model);
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

    public boolean checkAll(){
        return cbAll.isChecked();
    }
    int sum=0;
    int checkout=0;

    private void checkOut(){
        if (listSelect.size()!=0){
            Intent i = new Intent(getContext(), PayActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(getContext(), "Select an product", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void total(CartModel model,boolean b) {
        if(b){
            sum += model.getQuantity()* model.getPrice();

            ProductModel objProduct = new ProductModel();
            objProduct.setName(model.getName());
            objProduct.setQuantity(model.getQuantity());
            objProduct.setPrice(model.getPrice());
            objProduct.setImage(model.getImage());
            listSelect.add(objProduct);
            checkout++;
        }else{
            sum -= model.getQuantity()* model.getPrice();
            ProductModel objProduct = new ProductModel();
            objProduct.setName(model.getName());
            objProduct.setQuantity(model.getQuantity());
            objProduct.setPrice(model.getPrice());
            objProduct.setImage(model.getImage());
            listSelect.removeIf(obj->obj.getName().equals(objProduct.getName()));
            checkout--;
        }
        tvCheckout.setText("Check out ("+checkout+")");
        tvTotal.setText(sum+" VNĐ");
    }
    boolean check = false;
    @Override
    public boolean checkCbAll() {
        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                check = b;
            }
        });
        return check;
    }

}