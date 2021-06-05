package com.example.barber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceCustomer;
    public static String number;
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_home ,container,false);

        firebaseAuth=firebaseAuth.getInstance();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceCustomer= FirebaseDatabase.getInstance().getReference().child("Customer").child(uid);

        databaseReferenceCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                number=dataSnapshot.child("number").getValue().toString();
                //Toast.makeText(getContext(),"Number"+number,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView saloon=(TextView)view.findViewById(R.id.saloon);
        saloon.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(),Booking.class);
        startActivity(intent);
    }
}
