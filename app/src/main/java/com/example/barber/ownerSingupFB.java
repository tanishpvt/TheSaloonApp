package com.example.barber;

public class ownerSingupFB {
    public String first_name,last_name,number,Email,Gender,Link;
    ownerSingupFB()
    {

    }

    public ownerSingupFB(String first_name, String last_name, String number, String email, String gender, String link) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.number = number;
        Email = email;
        if(gender.charAt(0)=='m')
            gender=gender.replace('m','M');
        if(gender.charAt(0)=='f')
            gender=gender.replace('f','F');
        Gender = gender;
        Link = link;
    }
}
