package com.example.barber;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerProfile extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    EditText fname,lname,sname,email,mnumber,sot,sct;
    DatabaseReference databaseReferenceShop,databaseReferenceOwner;
    private FirebaseAuth firebaseAuth;
    String userid=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_ownerprofile,container,false);
        fname=view.findViewById(R.id.fname);
        fname.setEnabled(false);
        lname=view.findViewById(R.id.Lname);
        lname.setEnabled(false);
        sname=view.findViewById(R.id.shopname);
        sname.setEnabled(false);
        email=view.findViewById(R.id.email);
        email.setEnabled(false);
        mnumber=view.findViewById(R.id.number);
        mnumber.setEnabled(false);
        sot=view.findViewById(R.id.ShopOpeningTime);
        sot.setEnabled(false);
        sct=view.findViewById(R.id.ShopClosingTime);
        sct.setEnabled(false);


        firebaseAuth=firebaseAuth.getInstance();
        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        userid=currentuser.getUid();


        databaseReferenceShop=FirebaseDatabase.getInstance().getReference().child("Shopdetails").child(userid);
        databaseReferenceOwner=FirebaseDatabase.getInstance().getReference().child("Owner").child(userid);
        databaseReferenceShop.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String saloon=dataSnapshot.child("name").getValue().toString();
                String opening=dataSnapshot.child("opening").getValue().toString();
                String closing=dataSnapshot.child("closing").getValue().toString();
                sname.setText(saloon);
                sot.setText(opening);
                sct.setText(closing);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstname=dataSnapshot.child("first_name").getValue().toString();
                String lastname=dataSnapshot.child("last_name").getValue().toString();
                String Email=dataSnapshot.child("Email").getValue().toString();
                String Number=dataSnapshot.child("number").getValue().toString();
                fname.setText(firstname);
                lname.setText(lastname);
                email.setText(Email);
                mnumber.setText(Number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
