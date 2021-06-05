package com.example.barber;

public class Customer {
    public String First_Name,Last_Name,number,email,gender,referral_code;

    public Customer()
    {

    }
    public Customer(String first_Name, String last_Name, String number, String email, String gender, String referral_code) {
        First_Name = first_Name;
        Last_Name = last_Name;
        this.number = number;
        this.email = email;
        if(gender.charAt(0)=='m')
            gender=gender.replace('m','M');
        if(gender.charAt(0)=='f')
            gender=gender.replace('f','F');
        this.gender = gender;
        this.referral_code = referral_code;
    }
}
