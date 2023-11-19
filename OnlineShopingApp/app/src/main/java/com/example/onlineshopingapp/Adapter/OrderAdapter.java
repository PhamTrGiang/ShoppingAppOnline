package com.example.onlineshopingapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Model.OrderModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends BaseAdapter {
    ArrayList<OrderModel> list;
    Context context;
    int role;

    public void setTableItems(ArrayList<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public OrderAdapter(ArrayList<OrderModel> list, Context context, int role) {
        this.list = list;
        this.context = context;
        this.role = role;
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
            classView =View.inflate(viewGroup.getContext(), R.layout.item_order,null);
        }else{
            classView = view;
        }
        final OrderModel objOrder = list.get(i);
        TextView tvStatus = classView.findViewById(R.id.tvStatus);
        TextView tvTotal = classView.findViewById(R.id.tvTotal);
        TextView tvSize = classView.findViewById(R.id.tvSize);
        Button btnStatus = classView.findViewById(R.id.btnStatus);
        GridView gridView = classView.findViewById(R.id.gridView);

        OrderProductAdapter adapter = new OrderProductAdapter(context,objOrder.getProduct());
        gridView.setAdapter(adapter);

        String status = objOrder.getStatus()==0?"To Pay":"To Receive";
        int sum = 0;

        for(ProductModel list : objOrder.getProduct()){
            sum+=list.getPrice()*list.getQuantity();
        }
        tvSize.setText(objOrder.getProduct().size()+" item");
        tvTotal.setText(sum+" VNĐ");
        if (role==1){
            if(objOrder.getStatus()==0){
                btnStatus.setVisibility(View.VISIBLE);
                btnStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        objOrder.setStatus(1);
                        ApiService.apiService.setOrder(objOrder).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {
                                    btnStatus.setVisibility(View.GONE);
                                    tvStatus.setText("To Receive");
                                    Toast.makeText(context, "Accept order successfull", Toast.LENGTH_SHORT).show();
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
            }else{
                btnStatus.setVisibility(View.GONE);
            }
        }

        tvStatus.setText(status);
        return classView;
    }


}
