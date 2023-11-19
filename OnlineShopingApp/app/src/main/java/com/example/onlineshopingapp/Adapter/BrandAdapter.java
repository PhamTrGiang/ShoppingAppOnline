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

public class BrandAdapter extends BaseAdapter {
    ArrayList<BrandModel> list;

    private Callback callback;
    Context context;


    public BrandAdapter(ArrayList<BrandModel> list,Callback callback,Context context) {
        this.list = list;
        this.callback = callback;
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
            classView =View.inflate(viewGroup.getContext(), R.layout.item_brand,null);
        }else{
            classView = view;
        }

        final BrandModel objBrand = list.get(i);

        ImageView imgBrand = classView.findViewById(R.id.imgBrand);
        TextView tvBrand = classView.findViewById(R.id.tvBrand);
        ImageView imgDel = classView.findViewById(R.id.imgDel);
        ImageView imgEdit = classView.findViewById(R.id.imgEdit);



        Picasso.with(context)
                .load(objBrand.getImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_hide_image_24)
                .fit()
                .into(imgBrand);

        tvBrand.setText(objBrand.getName());


        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.delete(objBrand);
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.edit(objBrand);
            }
        });


        return classView;
    }

    public  interface Callback{
        void edit(BrandModel model);
        void delete(BrandModel model);
    }

}
