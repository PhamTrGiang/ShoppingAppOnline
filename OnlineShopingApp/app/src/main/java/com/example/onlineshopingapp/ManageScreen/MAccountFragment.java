package com.example.onlineshopingapp.ManageScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.ProductAdapter;
import com.example.onlineshopingapp.Adapter.SpinnerAdapter;
import com.example.onlineshopingapp.Adapter.UserAdapter;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Model.UserModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MAccountFragment extends Fragment {

    GridView gridView;
    UserAdapter adapter;

    public MAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_m_account, container, false);
        gridView = v.findViewById(R.id.gridView);
        adapter=new UserAdapter(new ArrayList<>());
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
        ApiService.apiService.getUser().enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<UserModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}