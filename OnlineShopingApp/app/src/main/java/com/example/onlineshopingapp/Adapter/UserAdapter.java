package com.example.onlineshopingapp.Adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Model.UserModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends BaseAdapter {
    private ArrayList<UserModel> list;

    public UserAdapter(ArrayList<UserModel> list) {
        this.list = list;
    }

    public void setTableItems(ArrayList<UserModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View classView;
        if (view == null){
            classView =View.inflate(viewGroup.getContext(), R.layout.item_user,null);
        }else{
            classView = view;
        }

        final UserModel objUser = list.get(i);

        TextView email = classView.findViewById(R.id.tvEmail);
        Button btnStatus = classView.findViewById(R.id.btnStatus);

        email.setText(objUser.getEmail());
        btnStatus.setText(objUser.getStatus()==0?"User":"Manager");

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objUser.setStatus(objUser.getStatus()==0?1:0);
                ApiService.apiService.setStatus(objUser).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            btnStatus.setText(objUser.getStatus()==0?"User":"Manager");
                        } else {
                            Log.d("MAIN", "Respone Fail" + response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("setStatusError", "onFailure: "+t);
                    }
                });
            }
        });

        return classView;
    }
}
