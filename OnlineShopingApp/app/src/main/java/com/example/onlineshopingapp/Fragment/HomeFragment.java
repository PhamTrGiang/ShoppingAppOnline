package com.example.onlineshopingapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.BrandAdapterUser;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    BrandAdapterUser adapter;
    GridView gridView;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        adapter = new BrandAdapterUser(new ArrayList<>(),getContext());
        gridView = v.findViewById(R.id.gridView);
        adapter = new BrandAdapterUser(new ArrayList<>(),getContext());
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

    private void loadData(){
        ApiService.apiService.getBrand().enqueue(new Callback<ArrayList<BrandModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BrandModel>> call, Response<ArrayList<BrandModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<BrandModel> arrItem = response.body();
                    setDataAdapter(arrItem);
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
}