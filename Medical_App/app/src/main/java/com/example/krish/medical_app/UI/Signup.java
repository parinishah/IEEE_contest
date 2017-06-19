package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Doctor;
import com.example.krish.medical_app.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by parini on 07-06-2017.
 */

public class Signup extends AppCompatActivity
{
    protected EditText email;
    protected EditText username;
    protected EditText password;
    protected EditText confirm_password;
    protected TextView login;
    protected TextView signup;
    protected DatabaseReference check_existing;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        check_existing = FirebaseDatabase.getInstance().getReference();

        email = (EditText)findViewById(R.id.editText_signup_email);
        username = (EditText)findViewById(R.id.editText_signup_username);
        password = (EditText)findViewById(R.id.editText_signup_password);
        confirm_password = (EditText) findViewById(R.id.editText_signup_repassword);
        login = (TextView) findViewById(R.id.textView_signup_login_btn);
        signup = (TextView) findViewById(R.id.textView_signup_signup_btn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check_existing.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username.getText().toString()).exists())
                        {
                            username.setText("");
                            password.setText("");
                            confirm_password.setText("");
                            email.setText("");
                            username.setHint("Username already exists!");
                        }
                        else
                        {
                            if(password.getText().toString().equals(confirm_password.getText().toString()))
                            { launch_doctor_profile();}
                            else
                            {
                                password.setText("");
                                confirm_password.setText("");
                                confirm_password.setHint("Password didn't match!");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });
    }

    public void launch_login()
    {
        startActivity(new Intent(this, Login.class));
    }
    public void launch_doctor_profile()
    {
        Intent i = new Intent(this,Doctor_profile.class);
        i.putExtra("username",username.getText().toString());
        i.putExtra("password",password.getText().toString());
        i.putExtra("email",email.getText().toString());
        startActivity(i);

    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

