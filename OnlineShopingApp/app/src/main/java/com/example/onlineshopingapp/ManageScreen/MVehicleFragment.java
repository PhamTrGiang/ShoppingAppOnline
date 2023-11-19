package com.example.onlineshopingapp.ManageScreen;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.ProductAdapter;
import com.example.onlineshopingapp.Adapter.SpinnerAdapter;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MVehicleFragment extends Fragment implements View.OnClickListener {
    GridView gridView;
    FloatingActionButton flAdd;
    SpinnerAdapter spinnerAdapter;
    ProductAdapter adapter;



    public MVehicleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_m_vehicle, container, false);
        gridView = v.findViewById(R.id.gridView);
        flAdd = v.findViewById(R.id.fl_add);
        flAdd.setOnClickListener(this);
        adapter = new ProductAdapter(getContext(),new ArrayList<>());
        gridView.setAdapter(adapter);

        spinnerAdapter = new SpinnerAdapter(new ArrayList<>());
        loadDataBrand();


        loadData();
        return v;
    }

    private void setDataAdapter(ArrayList arr){
        if (arr != null) {
            adapter.setTableItems(arr);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadData(){
        ApiService.apiService.getProduct().enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ProductModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadDataBrand(){
        ApiService.apiService.getBrand().enqueue(new Callback<ArrayList<BrandModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BrandModel>> call, Response<ArrayList<BrandModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<BrandModel> arrItemBrand = response.body();
                    if(arrItemBrand!=null){
                        spinnerAdapter.setTableItems(arrItemBrand);
                        spinnerAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<BrandModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProduct(String image,String name,int price,int quantity,String brand){
        ProductModel model = new ProductModel();
        model.setName(name);
        model.setImage(image);
        model.setPrice(price);
        model.setQuantity(quantity);
        model.setBrand(brand);

        ApiService.apiService.addProduct(model).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Add brand successful", Toast.LENGTH_SHORT).show();
                    ArrayList<ProductModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_product);

        EditText edImage = dialog.findViewById(R.id.edImage);
        EditText edName = dialog.findViewById(R.id.edName);
        EditText edPrice = dialog.findViewById(R.id.edPrice);
        EditText edQuantity = dialog.findViewById(R.id.edQuantity);
        Spinner spBrand = dialog.findViewById(R.id.spBrand);
        spBrand.setAdapter(spinnerAdapter);

        final String[] brandID = {""};
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BrandModel objBrand = (BrandModel) adapterView.getItemAtPosition(i);
                brandID[0] = objBrand.get_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String image = edImage.getText().toString();
                String name = edName.getText().toString();
                int price = Integer.parseInt(edPrice.getText().toString());
                int quantity = Integer.parseInt(edQuantity.getText().toString());
                String brand = brandID[0];

                addProduct(image,name,price,quantity,brand);

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}