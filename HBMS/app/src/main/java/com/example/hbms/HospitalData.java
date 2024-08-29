package com.example.hbms;

public class HospitalData {
    String HName;
    String password;
    String email;
    String phone;
    String address;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HospitalData() {
    }

    String latitude;
    String longitude;
    String image;
    String status;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    String total;
    String occupied;
    String vaccant;

    public HospitalData(String HName, String password, String email, String phone, String address, String latitude, String longitude, String image, String status) {
        this.HName = HName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.status = status;
    }

    public HospitalData(String total, String occupied, String vaccant) {
        this.total = total;
        this.occupied = occupied;
        this.vaccant = vaccant;
    }

    public String getHName() {
        return HName;
    }

    public void setHName(String HName) {
        this.HName = HName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOccupied() {
        return occupied;
    }

    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }


    public String getVaccant() {
        return vaccant;
    }

    public void setVaccant(String vaccant) {
        this.vaccant = vaccant;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



}
