package com.example.adminapp;

public class Data {
    String total,occupied,vaccant,date,time;

    public Data() {
    }

    public Data(String total, String occupied, String vaccant, String date, String time) {
        this.total = total;
        this.occupied = occupied;
        this.vaccant = vaccant;
        this.date = date;
        this.time=time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
