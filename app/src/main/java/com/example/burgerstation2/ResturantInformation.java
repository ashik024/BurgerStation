package com.example.burgerstation2;

import com.google.firebase.firestore.Exclude;

public class ResturantInformation {

    String name;
    String city;
    String address;
    String DeliveryTime;
    String type;
    String imgUri;
    String RestuarantId;

    public ResturantInformation() {

    }
        @Exclude
    public String getRestuarantId() {
        return RestuarantId;
    }

    public void setRestuarantId(String restuarantId) {
        RestuarantId = restuarantId;
    }

    public ResturantInformation(String name, String city, String address, String DeliveryTime, String type, String imgUri) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.DeliveryTime = DeliveryTime;
        this.type = type;
        this.imgUri = imgUri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return DeliveryTime;
    }

    public void setPostal(String postal) {
        this.DeliveryTime = postal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
