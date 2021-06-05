package com.example.barber;

public class Shop_detailsFB {
    public String name,opening,closing,location,mobile_number;
    public double latitude,longitude;
    public Shop_detailsFB() {
    }

    public Shop_detailsFB(String name, String opening, String closing, String location, double latitude, double longitude,String mobile) {
        this.name = name;
        this.opening = opening;
        this.closing = closing;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mobile_number=mobile;
    }
}
