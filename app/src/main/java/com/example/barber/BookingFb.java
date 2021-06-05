package com.example.barber;

public class BookingFb {
    String email_id,saloon_name,book_date,book_month,book_year,book_hour,book_minute,book_ampm;
    String schedule_date,schedule_month,schedule_year,schedule_hour,schedule_minute,schedule,ampm,Date;
    String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};

    public BookingFb() {
    }

    public BookingFb(String email_id, String saloon_name, String book_date, String book_month, String book_year, String book_hour, String book_minute, String book_ampm, String schedule_date, String schedule_month, String schedule_year, String schedule_hour, String schedule_minute, String schedule, String ampm) {
        this.email_id=email_id;
        this.saloon_name = saloon_name;
        this.book_date = book_date;
        int m=Integer.parseInt(book_month.trim());
        this.book_month = months[m-1];
        this.book_year = book_year;
        this.book_hour = book_hour;
        this.book_minute = book_minute;
        this.book_ampm = book_ampm;
        this.schedule_date = schedule_date;
        m=Integer.parseInt(schedule_month.trim());
        this.schedule_month = months[m-1];
        this.schedule_year = schedule_year;
        this.schedule_hour = schedule_hour;
        this.schedule_minute = schedule_minute;
        this.schedule = schedule;
        this.ampm = ampm;
    }
}
