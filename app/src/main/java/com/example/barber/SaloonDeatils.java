package com.example.barber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class SaloonDeatils extends AppCompatActivity {

    Button booking;
    TextView saloon,openingTime,closingTime;
    String saloonName,AMPM,saloondata,cAMPM,BookingDate,ScheduleDate,First_name,Last_name,Customer_mobile,Owner_mobile,Date;
    TextView general_cut,buzz_cut,combover,lowfade,trim,cleanshave,facemassage,classicmassage,Therapeuticmassage;
    TextView hair_straightining,facial,manicure,padicure,spa;
    int date,month,year,minute,hour,cdate,cmonth,cyear,cminute,chour;
    DatabaseReference databaseReferenceshop,databaseReferenceBooking,databaseReferencePrice,databaseReferenceCustomer;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_deatils);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        general_cut=findViewById(R.id.GeneralHaircut_detail);
        buzz_cut=findViewById(R.id.Buzz_hair_cut_detail);
        combover=findViewById(R.id.combover_haircut_detail);
        lowfade=findViewById(R.id.lowfade_haircut_detail);
        trim=findViewById(R.id.Trimming_detail);
        cleanshave=findViewById(R.id.cleanshave_detail);
        facemassage=findViewById(R.id.facemassage_detail);
        classicmassage=findViewById(R.id.classicmassage_detail);
        Therapeuticmassage=findViewById(R.id.Therapeuticmassage_detail);
        hair_straightining=findViewById(R.id.hairstraightining_detail);
        facial= findViewById(R.id.Facial_detail);
        manicure=findViewById(R.id.Manicure_detail);
        padicure= findViewById(R.id.Padicure_detail);
        spa=findViewById(R.id.Spa_detail);


        Calendar calendar=Calendar.getInstance();
        cminute=calendar.get(Calendar.MINUTE);
        chour=calendar.get(Calendar.HOUR_OF_DAY);
        cdate=calendar.get(Calendar.DAY_OF_MONTH);
        cmonth=calendar.get(Calendar.MONTH);
        cyear=calendar.get(Calendar.YEAR);

        String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        if(chour==0)
        {
            cAMPM="AM";
            chour=12;
        }
        else if(chour>0 && chour<12)
            cAMPM="AM";
        else if(chour==12)
            cAMPM="PM";
        else if(chour>12 && chour<=24)
        {
            cAMPM="PM";
            chour-=12;
        }
        BookingDate=cdate+" "+months[cmonth]+" "+cyear+" at "+chour+"."+cminute+" "+cAMPM;//Used To Display Booking Date

        date=NearbySaloonList.day_book;
        month=NearbySaloonList.Month_book;
        year=NearbySaloonList.Year_book;
        minute=NearbySaloonList.minute_book;
        hour=NearbySaloonList.hour_book;
        AMPM=Booking.ampm;

        saloondata=""+date+""+month+""+year+""+minute+""+hour+AMPM;//Used To Check if Slot Is Available At The Given Time At The Given Saloon
        ScheduleDate=date+" "+months[month]+" "+year+" at "+hour+"."+minute+" "+AMPM;//Used To Display Scheduled Date
        Date=date+" "+months[month]+" "+year;//Used To Find Today's Appointment

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceshop=FirebaseDatabase.getInstance().getReference().child("Shopdetails");
        databaseReferenceBooking= FirebaseDatabase.getInstance().getReference().child("Booking");
        databaseReferencePrice=FirebaseDatabase.getInstance().getReference().child("PriceChart");
        databaseReferenceCustomer=FirebaseDatabase.getInstance().getReference().child("Customer").child(uid);


        firebaseAuth=FirebaseAuth.getInstance();

        saloonName=getIntent().getStringExtra("SaloonName");
        //Log.v("SaloonName",saloonName);

        saloon=findViewById(R.id.saloonNameDetail);
        saloon.setText(saloonName);
        openingTime=findViewById(R.id.openTimedisplay);
        closingTime=findViewById(R.id.closeTimedisplay);

        databaseReferenceCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                First_name=dataSnapshot.child("First_Name").getValue().toString();
                Last_name=dataSnapshot.child("Last_Name").getValue().toString();
                Customer_mobile=dataSnapshot.child("number").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceshop.orderByChild("name").equalTo(saloonName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                openingTime.setText(dataSnapshot.child("opening").getValue().toString());
                closingTime.setText(dataSnapshot.child("closing").getValue().toString());
                Owner_mobile=dataSnapshot.child("mobile_number").getValue().toString();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferencePrice.orderByChild("saloon_name").equalTo(saloonName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot price, @Nullable String s) {
                general_cut.setText(price.child("general_cut").getValue().toString());
                buzz_cut.setText(price.child("buzz_cut").getValue().toString());
                combover.setText(price.child("combover").getValue().toString());
                lowfade.setText(price.child("lowfade").getValue().toString());
                trim.setText(price.child("trim").getValue().toString());
                cleanshave.setText(price.child("cleanshave").getValue().toString());
                facemassage.setText(price.child("facemassage").getValue().toString());
                classicmassage.setText(price.child("classicmassage").getValue().toString());
                Therapeuticmassage.setText(price.child("Therapeuticmassage").getValue().toString());
                hair_straightining.setText(price.child("hair_straightining").getValue().toString());
                facial.setText(price.child("facial").getValue().toString());
                manicure.setText(price.child("manicure").getValue().toString());
                padicure.setText(price.child("padicure").getValue().toString());
                spa.setText(price.child("spa").getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final HashMap<String,Integer> hm=new HashMap<>();

        final String USER_ID=uid+saloondata;

        booking=findViewById(R.id.BookSlot);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databaseReferenceBooking.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for(DataSnapshot postsnapshot:dataSnapshot.getChildren())
                       {
                           hm.put(postsnapshot.child("saloon_data").getValue().toString(),1);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
               if(hm.containsKey(saloondata))
               {
                   Toast.makeText(getApplicationContext(),"Sorry!!!Slot Not Available At This Time...",Toast.LENGTH_LONG).show();
               }
               else
               {
                   SaloonBooking saloonBooking=new SaloonBooking(saloonName,BookingDate,ScheduleDate,saloondata,First_name,Last_name,Customer_mobile,Owner_mobile,Date);

                   FirebaseDatabase.getInstance().getReference("Booking")
                           .child(USER_ID)
                           .setValue(saloonBooking)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Toast.makeText(getApplicationContext(),"Slot Booked Successfully!!!",Toast.LENGTH_SHORT).show();
                               }
                           });
               }
            }
        });

    }

}
