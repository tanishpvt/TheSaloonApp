package com.example.barber;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class OwnerRatechart extends Fragment
implements View.OnClickListener {
    EditText ghaircut,bhaircut,chaircut,low,trim,cs,fm,cm,tm,hs,fac,man,pad,sp;
    //SharedPreferences gh,bh,ch,lo,tr,csc,fmc,cmc,tmc,hsc,facc,manc,padc,spc;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_ratechart,container,false);

        SharedPreferences prefs=this.getContext().getSharedPreferences("Info",MODE_PRIVATE);




        String value="";
        ghaircut=view.findViewById(R.id.GeneralHaircut);
        value=prefs.getString("ghaircut",null);
        if(value!=null)
            ghaircut.setText(value);
        bhaircut=view.findViewById(R.id.Buzz_hair_cut);
        value=prefs.getString("bhaircut",null);
        if(value!=null)
            bhaircut.setText(value);
        chaircut=view.findViewById(R.id.combover_haircut);
        value=prefs.getString("chaircut",null);
        if(value!=null)
            chaircut.setText(value);
        low=view.findViewById(R.id.lowfade_haircut);
        value=prefs.getString("low",null);
        if(value!=null)
            low.setText(value);
        trim=view.findViewById(R.id.Trimming);
        value=prefs.getString("trim",null);
        if(value!=null)
            trim.setText(value);
        cs=view.findViewById(R.id.cleanshave);
        value=prefs.getString("cs",null);
        if(value!=null)
            cs.setText(value);
        fm=view.findViewById(R.id.facemassage);
        value=prefs.getString("fm",null);
        if(value!=null)
            fm.setText(value);
        cm=view.findViewById(R.id.classicmassage);
        value=prefs.getString("cm",null);
        if(value!=null)
            cm.setText(value);
        tm=view.findViewById(R.id.Therapeuticmassage);
        value=prefs.getString("tm",null);
        if(value!=null)
            tm.setText(value);
        hs=view.findViewById(R.id.hairstraightining);
        value=prefs.getString("hs",null);
        if(value!=null)
            hs.setText(value);
        fac=view.findViewById(R.id.Facial);
        value=prefs.getString("fac",null);
        if(value!=null)
            fac.setText(value);
        man=view.findViewById(R.id.Manicure);
        value=prefs.getString("man",null);
        if(value!=null)
            man.setText(value);
        pad=view.findViewById(R.id.Padicure);
        value=prefs.getString("pad",null);
        if(value!=null)
            pad.setText(value);
        sp=view.findViewById(R.id.Spa);
        value=prefs.getString("sp",null);
        if(value!=null)
            sp.setText(value);


        Button button=(Button)view.findViewById(R.id.dataUpdate);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor edit=this.getActivity().getSharedPreferences("Info",MODE_PRIVATE).edit();
        final String generalHaircut=ghaircut.getText().toString().trim();
        edit.putString("ghaircut",generalHaircut);
        final String buzzHaircut=(bhaircut).getText().toString().trim();
        edit.putString("bhaircut",buzzHaircut);
        final String comboverHaircut=(chaircut).getText().toString().trim();
        edit.putString("chaircut",comboverHaircut);
        final String lowfade=low.getText().toString().trim();
        edit.putString("low",lowfade);
        final String trimming=trim.getText().toString().trim();
        edit.putString("trim",trimming);
        final String cleanShave=(cs).getText().toString().trim();
        edit.putString("cs",cleanShave);
        final String facemassage=(fm).getText().toString().trim();
        edit.putString("fm",facemassage);
        final String classicmassage=cm.getText().toString().trim();
        edit.putString("cm",classicmassage);
        final String therapeuticmassage=tm.getText().toString().trim();
        edit.putString("tm",therapeuticmassage);
        final String hairstraightining=(hs).getText().toString().trim();
        edit.putString("hs",hairstraightining);
        final String facial=(fac).getText().toString().trim();
        edit.putString("fac",facial);
        final String manicure=man.getText().toString().trim();
        edit.putString("man",manicure);
        final String padicure=pad.getText().toString().trim();
        edit.putString("pad",padicure);
        final String spa=sp.getText().toString().trim();
        edit.putString("sp",spa);


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference=database.getReference("PriceChart");
        final String[] saloon_name = new String[1];
        DatabaseReference databaseReferenceOwner=database.getReference("Shopdetails").child(uid);
        databaseReferenceOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saloon_name[0] =dataSnapshot.child("name").getValue().toString();
                RateChartFB info=new RateChartFB(saloon_name[0],generalHaircut,buzzHaircut,comboverHaircut,lowfade,trimming,cleanShave,facemassage,classicmassage,therapeuticmassage,hairstraightining,facial,manicure,padicure,spa);
                databaseReference.child(uid).setValue(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),"Updated Successfully!!!",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Try again",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        edit.commit();

    }
}
