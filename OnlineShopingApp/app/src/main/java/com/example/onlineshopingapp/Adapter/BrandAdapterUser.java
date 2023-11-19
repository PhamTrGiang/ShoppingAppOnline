package com.example.onlineshopingapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandAdapterUser extends BaseAdapter {
    ArrayList<BrandModel> list;
    Context context;


    public BrandAdapterUser(ArrayList<BrandModel> list, Context context) {
        this.list = list;

        this.context = context;
    }

    public void setTableItems(ArrayList<BrandModel> list) {
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
            classView =View.inflate(viewGroup.getContext(), R.layout.item_brand_user,null);
        }else{
            classView = view;
        }

        final BrandModel objBrand = list.get(i);

        ImageView imgBrand = classView.findViewById(R.id.imgBrand);
        TextView tvBrand = classView.findViewById(R.id.tvBrand);

        Picasso.with(context)
                .load(objBrand.getImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_hide_image_24)
                .fit()
                .into(imgBrand);

        tvBrand.setText(objBrand.getName());


        return classView;
    }
}
