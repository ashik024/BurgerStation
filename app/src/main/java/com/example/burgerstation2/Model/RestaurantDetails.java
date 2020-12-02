package com.example.burgerstation2.Model;

public class RestaurantDetails {
    String RestaurantName;
    String RestaurantAddress;
    String Cover;
    String RestaurantDeliveryType;
    String RestaurantNameDetails;


    public RestaurantDetails() {

    }

    public RestaurantDetails(String restaurantName, String restaurantAddress, String cover, String restaurantDeliveryType, String restaurantNameDetails) {
        RestaurantName = restaurantName;
        RestaurantAddress = restaurantAddress;
        Cover = cover;
        RestaurantDeliveryType = restaurantDeliveryType;
        RestaurantNameDetails = restaurantNameDetails;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getRestaurantDeliveryType() {
        return RestaurantDeliveryType;
    }

    public void setRestaurantDeliveryType(String restaurantDeliveryType) {
        RestaurantDeliveryType = restaurantDeliveryType;
    }

    public String getRestaurantNameDetails() {
        return RestaurantNameDetails;
    }

    public void setRestaurantNameDetails(String restaurantNameDetails) {
        RestaurantNameDetails = restaurantNameDetails;
    }
}
