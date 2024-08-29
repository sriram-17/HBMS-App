package com.example.hbms;

public class Model {
    String hname,email,address,phone,image;

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Model() {
    }

    public Model(String hname, String email, String address, String phone, String image) {
        this.hname = hname;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }
}
