package com.example.onlineshopingapp.Model;

import java.util.ArrayList;

public class OrderModel {
    String _id,userId,address;
    ArrayList<ProductModel> product;
    int status;

    public OrderModel() {
    }

    public OrderModel(String _id, String userId, String address, ArrayList<ProductModel> product) {
        this._id = _id;
        this.userId = userId;
        this.address = address;
        this.product = product;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<ProductModel> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductModel> product) {
        this.product = product;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
