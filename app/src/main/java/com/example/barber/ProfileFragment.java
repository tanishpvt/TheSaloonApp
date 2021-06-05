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

public class ProfileFragment extends Fragment
{
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    EditText fname,lname,email,mn,gender;
    DatabaseReference databaseReferenceCustomer;
    FirebaseAuth firebaseAuth;
    String userid=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        fname=view.findViewById(R.id.fnameUser);
        fname.setEnabled(false);
        lname=view.findViewById(R.id.LnameUser);
        lname.setEnabled(false);
        email=view.findViewById(R.id.emailUser);
        email.setEnabled(false);
        mn=view.findViewById(R.id.numberUser);
        mn.setEnabled(false);
        gender=view.findViewById(R.id.GenderUser);
        gender.setEnabled(false);

        firebaseAuth=firebaseAuth.getInstance();
        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        userid=currentuser.getUid();

        databaseReferenceCustomer= FirebaseDatabase.getInstance().getReference().child("Customer").child(userid);
        databaseReferenceCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstname=dataSnapshot.child("First_Name").getValue().toString().trim();
                fname.setText(firstname);
                String lastname=dataSnapshot.child("Last_Name").getValue().toString().trim();
                lname.setText(lastname);
                String Email=dataSnapshot.child("email").getValue().toString().trim();
                email.setText(Email);
                String number=dataSnapshot.child("number").getValue().toString().trim();
                mn.setText(number);
                String sex=dataSnapshot.child("gender").getValue().toString().trim();
                if(sex.charAt(0)=='M')
                    gender.setText("Male");
                else
                    gender.setText("Female");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;

    }
}
