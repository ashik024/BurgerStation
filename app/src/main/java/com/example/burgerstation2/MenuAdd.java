package com.example.burgerstation2;

class MenuAdd {

    String id;
    String name;
    String des;
    String price;


    public MenuAdd(){

    }

    public MenuAdd(String id, String name, String des, String price) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getPrice() {
        return price;
    }
}
