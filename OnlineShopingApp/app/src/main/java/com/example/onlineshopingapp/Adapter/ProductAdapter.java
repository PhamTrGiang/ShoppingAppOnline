package com.example.onlineshopingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;

import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Product.ProductDetail;
import com.example.onlineshopingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProductModel> list;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setTableItems(ArrayList<ProductModel> list) {
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
            classView =View.inflate(viewGroup.getContext(), R.layout.item_product,null);
        }else{
            classView = view;
        }

        final ProductModel objProduct = list.get(i);

        TextView name = classView.findViewById(R.id.tvName);
        TextView price = classView.findViewById(R.id.tvPrice);
        ImageView imgVehicle = classView.findViewById(R.id.imgView);
        CardView cardView = classView.findViewById(R.id.cartView);

        name.setText(objProduct.getName());
        price.setText(objProduct.getPrice()+" VNĐ");

        Picasso.with(context)
                .load(objProduct.getImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_hide_image_24)
                .fit()
                .into(imgVehicle);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProductDetail.class);
                i.putExtra("id",objProduct.get_id());
                context.startActivity(i);
            }
        });

        return classView;
    }
}
