package com.example.barber;

public class SaloonBooking {
    public String saloon_name,booking_time,scheduling_time,saloon_data,user_first_name,user_last_name,Customer_Mobile_Number,Owner_Mobile_Number,Date;

    public SaloonBooking() {
    }

    public SaloonBooking(String saloon_name, String booking_time, String scheduling_time, String saloon_data, String user_first_name, String user_last_name, String customer_Mobile_Number, String owner_Mobile_Number,String Date) {
        this.saloon_name = saloon_name;
        this.booking_time = booking_time;
        this.scheduling_time = scheduling_time;
        this.saloon_data = saloon_data;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        Customer_Mobile_Number = customer_Mobile_Number;
        Owner_Mobile_Number = owner_Mobile_Number;
        this.Date=Date;
    }
}
