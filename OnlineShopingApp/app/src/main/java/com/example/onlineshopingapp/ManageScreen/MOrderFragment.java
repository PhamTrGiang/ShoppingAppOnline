package com.example.onlineshopingapp.ManageScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.OrderAdapter;
import com.example.onlineshopingapp.Model.OrderModel;
import com.example.onlineshopingapp.Product.OrderActivity;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MOrderFragment extends Fragment {
    GridView gridView;
    OrderAdapter adapter;

    public MOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_m_order, container, false);
        gridView = v.findViewById(R.id.gridView);
        adapter = new OrderAdapter(new ArrayList<>(),getContext(),1);
        gridView.setAdapter(adapter);

        return v;
    }

    private void setDataAdapter(ArrayList arr){
        if (arr != null) {
            adapter.setTableItems(arr);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadData(){
        ApiService.apiService.getOrder("").enqueue(new Callback<ArrayList<OrderModel>>() {
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
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}