package com.example.burgerstation2;

public class OrderList {

    String Name;
    String Phone;
    String Date;
    String Time;
    String TotalPrice;
    String Address;
    String State;
    String Id;

    public OrderList() {
    }

    public OrderList(String name, String phone, String date, String time, String totalPrice, String address, String state, String id) {
        Name = name;
        Phone = phone;
        Date = date;
        Time = time;
        TotalPrice = totalPrice;
        Address = address;
        State = state;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
