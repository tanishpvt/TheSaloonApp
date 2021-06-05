package com.example.barber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signin_activity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    DatabaseReference databaseReferenceCustomer,databaseReferenceOwner;
    String email,password;
    public static String Owneruid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        inputEmail = (EditText) findViewById(R.id.email);
        email=inputEmail.getText().toString().trim();
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.sign_in_button);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnSignup=(Button) findViewById(R.id.sign_up_button);
    }

    //It takes the user to ResetPassword Page
    public void launchResetPasswordActivity(View view) {
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }

    //If the user doesn't have an account it takes the user to signup page
    public void gotoRegister(View view) {
        Intent i=new Intent(this,Sign_up.class);
        startActivity(i);
    }

    //Function to take the user or owner to their respective homepage
    public void SignIn(View view) {
        auth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        email = inputEmail.getText().toString().trim();
        password=inputPassword.getText().toString().trim();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(Signin_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            if(password.length()<6)
                            {
                                inputPassword.setError(getString(R.string.minimum_password));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                        databaseReferenceCustomer= FirebaseDatabase.getInstance().getReference().child("Customer");
                        databaseReferenceOwner=FirebaseDatabase.getInstance().getReference().child("Owner");
                        databaseReferenceOwner.orderByChild("Email").equalTo(email).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                Intent intent=new Intent(getApplicationContext(),Owner.class);
                                Owneruid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                //Owneruid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                //Toast.makeText(getApplicationContext(),"Ownerid"+Owneruid,Toast.LENGTH_LONG).show();
                                //intent.putExtra("UserId","uid");
                                startActivity(intent);
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

                        databaseReferenceCustomer.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                Intent i=new Intent(getApplicationContext(),UserHomePage.class);

                                //i.putExtra("UserId","uid");
                                startActivity(i);
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

                        //Toast.makeText(getApplicationContext(),"Please enter a valid user email",Toast.LENGTH_LONG).show();
                        }
                }

                }  );
    }
}
