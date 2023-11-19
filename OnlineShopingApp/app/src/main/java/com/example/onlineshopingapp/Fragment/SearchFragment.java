package com.example.onlineshopingapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.BrandAdapter;
import com.example.onlineshopingapp.Adapter.ProductAdapter;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    GridView gridView;
    ProductAdapter adapter;
    
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        gridView = v.findViewById(R.id.gridView);
        adapter = new ProductAdapter(getContext(),new ArrayList<>());
        gridView.setAdapter(adapter);
        loadData();
        return v;
    }
    private void setDataAdapter(ArrayList arr){
        if (arr != null) {
            adapter.setTableItems(arr);
            adapter.notifyDataSetChanged();
        }
    }
    public void loadData(){
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
}