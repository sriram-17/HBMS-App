package com.example.hbms;

public class UserHelper {

    String userID,fName,password,email,phone,age,gender,type;
    public UserHelper() {
    }


    public UserHelper(String userID, String fName, String password, String email, String phone, String age, String gender, String type) {
        this.userID = userID;
        this.fName = fName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}