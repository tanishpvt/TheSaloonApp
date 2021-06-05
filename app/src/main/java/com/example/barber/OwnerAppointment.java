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

public class OwnerAppointment extends Fragment
{
    ArrayList<String> Customer_name,booking_date,schedule_date,Mobile_number;
    TextView Nodata;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceBooking;
    String Saloon_Name;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_ownerappointment ,container,false);

        Nodata=view.findViewById(R.id.NodataOwner);

        firebaseAuth=firebaseAuth.getInstance();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceBooking= FirebaseDatabase.getInstance().getReference().child("Booking");

        final ListView customList=view.findViewById(R.id.CustomerlistOwner);
        customList.setVisibility(View.GONE);

        Customer_name=new ArrayList<>();
        booking_date=new ArrayList<>();
        schedule_date=new ArrayList<>();
        Mobile_number=new ArrayList<>();

        Saloon_Name=OwnerHome.Saloon_Name;

        databaseReferenceBooking.orderByChild("saloon_name").equalTo(Saloon_Name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    Nodata.setVisibility(View.GONE);
                    customList.setVisibility(View.VISIBLE);
                    String f_name = dataSnapshot.child("user_first_name").getValue().toString();
                    String l_name = dataSnapshot.child("user_last_name").getValue().toString();
                    String u_name = f_name + " " + l_name;
                    Customer_name.add(u_name);
                    booking_date.add(dataSnapshot.child("booking_time").getValue().toString());
                    schedule_date.add(dataSnapshot.child("scheduling_time").getValue().toString());
                    Mobile_number.add(dataSnapshot.child("Owner_Mobile_Number").getValue().toString());

                    OMyListAdapter oMyListAdapter=new OMyListAdapter(getContext(),Customer_name,booking_date,schedule_date,Mobile_number);
                    customList.setAdapter(oMyListAdapter);
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

        OMyListAdapter oMyListAdapter=new OMyListAdapter(getContext(),Customer_name,booking_date,schedule_date,Mobile_number);
        customList.setAdapter(oMyListAdapter);
        return view;
    }
}
