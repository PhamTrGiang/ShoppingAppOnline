package com.example.onlineshopingapp.Model;

public class CartModel {
    private String _id,productId,userId,name,image;
    private int quantity,price;

    public CartModel() {
    }

    public CartModel(String _id, String productId, String userId, String name, String image, int quantity, int price) {
        this._id = _id;
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
