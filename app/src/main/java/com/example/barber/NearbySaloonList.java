package com.example.barber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NearbySaloonList extends AppCompatActivity {
    ArrayList<String> saloon_name,avg_price,rate;
    double currentLongitude,currentLatitude;

    DatabaseReference databaseReferenceshop;
    FirebaseAuth firebaseAuth;

    NearbySaloonListAdapter nearbySaloonListAdapter;

    TextView Nodata;
    public static int day_book,Month_book,Year_book,minute_book,hour_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_saloon_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth=firebaseAuth.getInstance();
        databaseReferenceshop= FirebaseDatabase.getInstance().getReference().child("Shopdetails");

        Nodata=findViewById(R.id.Nodata);
        final ListView saloonList = findViewById(R.id.NearbySaloonList);

        saloon_name=new ArrayList<>();
        avg_price=new ArrayList<>();
        rate=new ArrayList<>();

        saloonList.setVisibility(View.GONE);
        saloonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),SaloonDeatils.class);
                intent.putExtra("SaloonName",saloon_name.get(position));
                startActivity(intent);
            }
        });

        currentLatitude=getIntent().getDoubleExtra("latitude",25.6193885);
        currentLongitude=getIntent().getDoubleExtra("longitude",85.171428);
        day_book=getIntent().getIntExtra("date",0);
        Month_book=getIntent().getIntExtra("month",0);
        Year_book=getIntent().getIntExtra("year",0);
        minute_book=getIntent().getIntExtra("minute",0);
        hour_book=getIntent().getIntExtra("hour",0);
        //Toast.makeText(getApplicationContext(),day_book+" "+Month_book+" "+Year_book,Toast.LENGTH_LONG).show();

        databaseReferenceshop.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    double lat=Double.parseDouble(postSnapshot.child("latitude").getValue().toString().trim());
                    double lon=Double.parseDouble(postSnapshot.child("longitude").getValue().toString().trim());

                    float[] results=new float[1];

                    android.location.Location.distanceBetween(lat,lon,currentLatitude,currentLongitude,results);
                    float distance=results[0];

                    Log.e("Distance",Float.toString(distance));

                    if(distance<2000)
                    {
                        Nodata.setVisibility(View.GONE);
                        saloonList.setVisibility(View.VISIBLE);
                        saloon_name.add(postSnapshot.child("name").getValue().toString());
                        avg_price.add("80 Rs.");
                        rate.add("3.5 star");
                        nearbySaloonListAdapter=new NearbySaloonListAdapter(getApplicationContext(),saloon_name,avg_price,rate);
                        saloonList.setAdapter(nearbySaloonListAdapter);

                        //nearbySaloonListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
