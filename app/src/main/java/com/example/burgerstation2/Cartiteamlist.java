package com.example.burgerstation2;

public class Cartiteamlist {


    String ProductId;
    String ProductName;
    String ProductPrice;
    String ProductQuantity;
    String SpiceLevel;
    String OrderDate;
    String OrderTime;
    String Advice;
    String Discount;
    String ResturantName;

    public Cartiteamlist() {
    }

    public Cartiteamlist(String productId, String productName, String productPrice, String productQuantity, String spiceLevel, String orderDate, String orderTime, String advice, String discount, String resturantName) {
        ProductId = productId;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
        SpiceLevel = spiceLevel;
        OrderDate = orderDate;
        OrderTime = orderTime;
        Advice = advice;
        Discount = discount;
        ResturantName = resturantName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getSpiceLevel() {
        return SpiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        SpiceLevel = spiceLevel;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getAdvice() {
        return Advice;
    }

    public void setAdvice(String advice) {
        Advice = advice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getResturantName() {
        return ResturantName;
    }

    public void setResturantName(String resturantName) {
        ResturantName = resturantName;
    }
}
