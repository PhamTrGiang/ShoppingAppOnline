package com.example.onlineshopingapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineshopingapp.Model.CartModel;
import com.example.onlineshopingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    ArrayList<CartModel> list;
    private Callback callback;
    Context context;

    public CartAdapter(ArrayList<CartModel> list,Callback callback, Context context) {
        this.list = list;
        this.callback = callback;
        this.context = context;
    }

    public void setTableItems(ArrayList<CartModel> list) {
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
            classView =View.inflate(viewGroup.getContext(), R.layout.item_cart,null);
        }else{
            classView = view;
        }

        final CartModel objCart = list.get(i);

        ImageView imgCart = classView.findViewById(R.id.imgCart);
        TextView tvName = classView.findViewById(R.id.tvName);
        TextView tvPrice = classView.findViewById(R.id.tvPrice);
        TextView tvQuantity = classView.findViewById(R.id.tvQuantity);
        CheckBox cbCart = classView.findViewById(R.id.cbCart);

        ImageView imgDel = classView.findViewById(R.id.imgDel);
        ImageView imgEdit = classView.findViewById(R.id.imgEdit);



        Picasso.with(context)
                .load(objCart.getImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_hide_image_24)
                .fit()
                .into(imgCart);

        tvName.setText(objCart.getName());
        tvPrice.setText(objCart.getPrice()+"VNĐ");
        tvQuantity.setText("Quantity: "+objCart.getQuantity());
        if(callback.checkCbAll()){
            cbCart.setChecked(true);
        }

        cbCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                callback.total(objCart,b);
            }
        });

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.delete(objCart);
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.edit(objCart);
            }
        });


        return classView;
    }

    public  interface Callback{
        void edit(CartModel model);
        void delete(CartModel model);
        void total(CartModel model,boolean b);
        boolean checkCbAll();
    }
}
