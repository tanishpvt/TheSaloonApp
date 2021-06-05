package com.example.barber;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class OwnerHome extends Fragment implements View.OnClickListener {
    ArrayList<String> Customer_name,booking_date,schedule_time,Contact_Details;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceBooking,databaseReferenceShop;

    int date,month,year;
    public static String Date,Saloon_Name;

    TextView Nodata;
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_ownerhome ,container,false);

        firebaseAuth=firebaseAuth.getInstance();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceBooking= FirebaseDatabase.getInstance().getReference().child("Booking");
        databaseReferenceShop=FirebaseDatabase.getInstance().getReference().child("Shopdetails");

        Customer_name=new ArrayList<>();
        booking_date=new ArrayList<>();
        schedule_time=new ArrayList<>();
        Contact_Details=new ArrayList<>();

        Nodata=view.findViewById(R.id.NodataToday);

        final ListView CustomerList = view.findViewById(R.id.CustomerlistToday);
        CustomerList.setVisibility(View.GONE);

        Calendar calendar=Calendar.getInstance();
        date=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);

        String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        Date=date+" "+months[month]+" "+year;

        databaseReferenceShop.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {
                Saloon_Name=Snapshot.child("name").getValue().toString();
                databaseReferenceBooking.orderByChild("saloon_name").equalTo(Saloon_Name).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if(dataSnapshot.exists() && dataSnapshot.child("Date").getValue().toString().equals(Date)) {
                            Nodata.setVisibility(View.GONE);
                            CustomerList.setVisibility(View.VISIBLE);
                            String f_name = dataSnapshot.child("user_first_name").getValue().toString();
                            String l_name = dataSnapshot.child("user_last_name").getValue().toString();
                            String u_name = f_name + " " + l_name;
                            Customer_name.add(u_name);
                            booking_date.add(dataSnapshot.child("booking_time").getValue().toString());
                            String[] str = dataSnapshot.child("scheduling_time").getValue().toString().split(" ");
                            String time = str[4] + " " + str[5];
                            schedule_time.add(time);
                            Contact_Details.add(dataSnapshot.child("Customer_Mobile_Number").getValue().toString());

                            ListAdapterToday listAdapterToday = new ListAdapterToday(getContext(), Customer_name, booking_date, schedule_time, Contact_Details);
                            CustomerList.setAdapter(listAdapterToday);
                        }
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
