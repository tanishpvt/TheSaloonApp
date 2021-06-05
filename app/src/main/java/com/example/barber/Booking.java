package com.example.barber;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Booking extends AppCompatActivity {
    static final int TIME_DIALOG_ID = 1111;
    private int hr,min;
    public static String ampm,Date;
    public TextView view1,view2;
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    List<Address> addresses;
    double latitude,longitude;
    TextView location_detail;
    String month;
    int day,Month,Year,minute,hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        view1=(TextView)findViewById(R.id.Date);
        view2=(TextView)findViewById(R.id.Time);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=
                        new DatePickerDialog(Booking.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {

                            @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                day=dayOfMonth;
                                Month=month;
                                Year=year;
                            view1.setText(year+"\\"+(month+1)+"\\"+dayOfMonth);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
        view2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Booking.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>=12)
                        {
                            ampm="PM";
                            hourOfDay-=12;
                        }
                        else
                        {
                            ampm="AM";
                        }
                        minute = minutes;
                        hour=hourOfDay;
                        view2.setText(String.format("%02d:%02d",hourOfDay,minutes)+ampm);
                        //view2.setText(hourOfDay+":"+minutes);
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
    }

    public boolean Location(View view) {
        requestPermission();
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    try {
                        addresses=geocoder.getFromLocation(latitude,longitude,1);
                        String address=addresses.get(0).getAddressLine(0);
                        String city=addresses.get(0).getLocality();
                        location_detail=(TextView)findViewById(R.id.locationdata);
                        location_detail.setText(address);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        return true;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }


    public void SaloonList(View view) {
        //String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        //Toast.makeText(getApplicationContext(),day+" "+months[Month]+" "+Year,Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,NearbySaloonList.class);

        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        intent.putExtra("date",day);
        intent.putExtra("month",Month);
        intent.putExtra("year",Year);
        intent.putExtra("minute",minute);
        intent.putExtra("hour",hour);
        startActivity(intent);
    }
}
