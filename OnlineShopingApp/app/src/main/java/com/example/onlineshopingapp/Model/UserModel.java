package com.example.onlineshopingapp.Model;

public class UserModel {
    String _id,email,password;
    int status;

    public UserModel() {
    }

    public UserModel(String _id, String email, String password, int status) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
