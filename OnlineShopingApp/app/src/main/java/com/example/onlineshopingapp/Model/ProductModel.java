package com.example.onlineshopingapp.Model;

public class ProductModel {
    private String _id,name,image,brand;
    private int quantity,price;

    public ProductModel() {
    }

    public ProductModel(String _id, String name, String image, String brand, int quantity, int price) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
