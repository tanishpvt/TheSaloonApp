package com.example.barber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void Customer(View view) {
        Intent i=new Intent(this,customerSignup.class);
        startActivity(i);
    }

    public void owner(View view) {
        Intent i=new Intent(this,ownerSignup.class);
        startActivity(i);
    }

    public void backToSignup(View view) {
        Intent i=new Intent(this, Signin_activity.class);
        startActivity(i);
    }
}
