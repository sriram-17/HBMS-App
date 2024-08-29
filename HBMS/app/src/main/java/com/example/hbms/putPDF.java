package com.example.hbms;

public class putPDF {
    String hname;
    String name;
    String email;
    String phone;
    String userID;
    String age;
    String bedType;
    String symptoms;
    String url;
    String key;
    String status;
    String userID_status;
    String hname_status;
    String qrUrl;

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public putPDF(String hname, String name, String email, String phone, String userID, String age, String bedType, String symptoms, String url, String key, String status, String userID_status, String hname_status, String qrUrl) {
        this.hname = hname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
        this.age = age;
        this.bedType = bedType;
        this.symptoms = symptoms;
        this.url = url;
        this.key = key;
        this.status = status;
        this.userID_status = userID_status;
        this.hname_status = hname_status;
        this.qrUrl = qrUrl;
    }

    public String getUserID_status() {
        return userID_status;
    }

    public String getHname_status() {
        return hname_status;
    }

    public putPDF(String hname, String name, String email, String phone, String userID, String age, String bedType, String symptoms, String url, String key, String status, String userID_status, String hname_status) {
        this.hname = hname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
        this.age = age;
        this.bedType = bedType;
        this.symptoms = symptoms;
        this.url = url;
        this.key = key;
        this.status = status;
        this.userID_status = userID_status;
        this.hname_status = hname_status;
    }

    public void setHname_status(String hname_status) {
        this.hname_status = hname_status;
    }

    public void setUserID_status(String userID_status) {
        this.userID_status = userID_status;
    }

    public putPDF(String hname, String name, String email, String phone, String userID, String age, String bedType, String symptoms, String url, String key, String status) {
        this.hname = hname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
        this.age = age;
        this.bedType = bedType;
        this.symptoms = symptoms;
        this.url = url;
        this.key = key;
        this.status = status;
    }

    public putPDF() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public putPDF(String hname, String name, String email, String phone, String userID, String age, String bedType, String symptoms, String url, String key) {
        this.hname = hname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
        this.age = age;
        this.bedType = bedType;
        this.symptoms = symptoms;
        this.url = url;
        this.key = key;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
