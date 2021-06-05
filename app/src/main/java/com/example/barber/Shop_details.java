package com.example.barber;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Shop_details extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    List<Address> addresses;
    static final int TIME_DIALOG_ID = 1111;
    public TextView view1,view2,location_detail;
    private int ohr,chr,omin,cmin;
    double latitude,longitude;
    public EditText name;
    String ampm,userid=null;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReferenceShop;
    public static String Owner_Saloon_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        Toolbar mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("Barber");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        view1=(TextView)findViewById(R.id.openingtime);
        view2=(TextView)findViewById(R.id.closingtime);
        view1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Shop_details.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>=12)
                        {
                            hourOfDay-=12;
                            ampm="PM";
                        }
                        else
                        {
                            ampm="AM";
                        }
                        view1.setText(String.format("%02d:%02d",hourOfDay,minutes)+ampm);
                        //view1.setText(hourOfDay+":"+minutes);
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
        view2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Shop_details.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>=12)
                        {
                            ampm="PM";
                            hourOfDay-=12;
                        }
                        else {
                            ampm="AM";
                        }
                        view2.setText(String.format("%02d:%02d",hourOfDay,minutes)+ampm);
                        //view2.setText(hourOfDay+":"+minutes);
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
    }


    public void homepage(View view) {
        firebaseAuth=firebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Shopdetails");
        userid=currentUser.getUid();
        name=(EditText) findViewById(R.id.shopname);
        view1=(TextView)findViewById(R.id.openingtime);
        view2=(TextView)findViewById(R.id.closingtime);
        location_detail=(TextView)findViewById(R.id.location);
        String sname=name.getText().toString().trim();
        String opentime=view1.getText().toString().trim();
        String closetime=view2.getText().toString().trim();
        String loc=location_detail.getText().toString().trim();
        if (TextUtils.isEmpty(sname)) {
            Toast.makeText(getApplicationContext(), "Please enter the saloon name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(opentime)) {
            Toast.makeText(getApplicationContext(), "Please enter your shop opening time!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(closetime)) {
            Toast.makeText(getApplicationContext(), "Please enter your shop closing time!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(loc)) {
            Toast.makeText(getApplicationContext(), "Please give your shop location", Toast.LENGTH_SHORT).show();
            return;
        }

        Owner_Saloon_Name=sname;
        Shop_detailsFB shopDetailsFB=new Shop_detailsFB(sname,opentime,closetime,loc,latitude,longitude,ownerSignup.Owner_Mobile_Number);
        databaseReference.child(userid).setValue(shopDetailsFB).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent i=new Intent(getApplicationContext(),Owner.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("not saved",e+" ");
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void onBackPressed()
    {

    }

    public boolean GetLocation(View view) {
        requestPermission();
        Context context;
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
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
                        location_detail=(TextView)findViewById(R.id.location);
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


}
