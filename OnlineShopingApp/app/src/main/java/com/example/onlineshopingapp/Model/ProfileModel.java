package com.example.onlineshopingapp.Model;

public class ProfileModel {
    String userId,name,phone,address;

    public ProfileModel() {
    }

    public ProfileModel(String userId, String name, String phone, String address) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
