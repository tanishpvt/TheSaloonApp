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

import java.util.ArrayList;

public class AppointmentFragment extends Fragment
{
    ArrayList<String> salon_name,booking_date,schedule_date,Contact;

    TextView Nodata;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceBooking;

    String number;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_appointment   ,container,false);

        firebaseAuth=firebaseAuth.getInstance();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
       // databaseReferenceCustomer=FirebaseDatabase.getInstance().getReference().child("Customer").child(uid);
        databaseReferenceBooking= FirebaseDatabase.getInstance().getReference().child("Booking");

        salon_name=new ArrayList<>();
        booking_date=new ArrayList<>();
        schedule_date=new ArrayList<>();
        Contact=new ArrayList<>();

        number=HomeFragment.number;

        Nodata=view.findViewById(R.id.NodataCustomer);

        final ListView customList=view.findViewById(R.id.Customerlist);
        customList.setVisibility(View.GONE);


        databaseReferenceBooking.orderByChild("Customer_Mobile_Number").equalTo(number).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    Nodata.setVisibility(View.GONE);
                    customList.setVisibility(View.VISIBLE);
                    salon_name.add(dataSnapshot.child("saloon_name").getValue().toString());
                    booking_date.add(dataSnapshot.child("booking_time").getValue().toString());
                    schedule_date.add(dataSnapshot.child("scheduling_time").getValue().toString());
                    Contact.add(dataSnapshot.child("Owner_Mobile_Number").getValue().toString());

                    CMyListAdapter cMyListAdapter=new CMyListAdapter(getContext(),salon_name,booking_date,schedule_date,Contact);
                    customList.setAdapter(cMyListAdapter);
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


        return view;
    }
}
