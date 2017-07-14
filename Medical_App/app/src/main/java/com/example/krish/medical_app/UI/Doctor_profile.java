package com.example.krish.medical_app.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Doctor;
import com.example.krish.medical_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by KRISH on 16-06-2017.
 */

public class Doctor_profile extends AppCompatActivity
{
    protected EditText fullname;
    protected EditText email;
    protected EditText mobile;
    protected EditText qualification;
    protected RadioButton male;
    protected RadioButton female;
    protected RadioButton others;
    protected ImageButton back;
    protected TextView save,doc_username;
    protected EditText college;
    protected Doctor doctor_obj;
    protected String signup_username,signup_email,signup_password,signup;
    protected DatabaseReference doc_profile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.doctor_profile);

        doc_profile = FirebaseDatabase.getInstance().getReference();

        final Bundle bundle = getIntent().getExtras();

        signup_email = bundle.getString("email");
        signup_password = bundle.getString("password");
        signup_username = bundle.getString("username");
        signup = bundle.getString("ui_status");

        doc_username = (TextView)findViewById(R.id.editText_doctor_username);
        fullname = (EditText)findViewById(R.id.editText_doctor_fullname);
        email = (EditText)findViewById(R.id.editText_doctor_email);
        mobile = (EditText)findViewById(R.id.editText_doctor_mobile);
        qualification = (EditText)findViewById(R.id.editText_doctor_qualification);
        college = (EditText)findViewById(R.id.editText_doctor_college);
        male = (RadioButton)findViewById(R.id.radio_doctor_male);
        female = (RadioButton)findViewById(R.id.radio_doctor_female);
        others = (RadioButton)findViewById(R.id.radio_doctor_other);
        back = (ImageButton) findViewById(R.id.imageButton_doctor_back);
        save = (TextView) findViewById(R.id.textView_doctor_save);


        doc_username.setText(signup_username);
        email.setText(signup_email);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(signup.equals("true"))
                {
                    launch_signup();
                }
                else if(signup.equals("false"))
                {
                    launch_my_patients(signup_username);
                }

                /*if(fullname.getText().toString().equals(null))
                {
                    fullname.setHintTextColor(Color.RED);
                    fullname.setHint("Required Qualification");
                }
                else if(qualification.getText().toString().equals(null))
                {
                    qualification.setHintTextColor(Color.RED);
                    qualification.setHint("Required Qualification");
                }
                else if(college.getText().toString().equals(null))
                {
                    college.setHintTextColor(Color.RED);
                    college.setHint("Required Qualification");
                }
                else if (mobile.getText().toString().equals(null))
                {
                    mobile.setHintTextColor(Color.RED);
                    mobile.setHint("Required Mobile Number");
                }
                else
                {
                    launch_my_patients(signup_username);
                }*/
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Gender = "NA";
                if (male.isChecked())
                {
                    Gender = "Male";
                }
                else if(female.isChecked())
                {
                    Gender = "Female";
                }
                else if(others.isChecked())
                {
                    Gender = "Other";
                }
                if(fullname.getText().toString().equals(""))
                {
                    fullname.setHintTextColor(Color.RED);
                    fullname.setHint("Required Full Name");
                }
                else if(qualification.getText().toString().equals(""))
                {
                    qualification.setHintTextColor(Color.RED);
                    qualification.setHint("Required Qualification");
                }
                else if(college.getText().toString().equals(""))
                {
                    college.setHintTextColor(Color.RED);
                    college.setHint("Required College");
                }
                else if (mobile.getText().toString().equals(""))
                {
                    mobile.setHintTextColor(Color.RED);
                    mobile.setHint("Required Mobile Number");
                }
                else
                {
                    doctor_obj = new Doctor(signup_username, signup_password, signup_email,
                            fullname.getText().toString(), college.getText().toString(), Gender, mobile.getText().toString(), qualification.getText().toString());
                    doctor_obj.firebase_doctor();

                    SharedPreferences sharedPref = getSharedPreferences("doctor_username", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("doctor_username", signup_username);
                    editor.commit();

                    launch_my_patients(signup_username);
                }

            }
        });

        doc_profile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(signup_username).exists())
                {
                    doc_username.setText(signup_username);
                    fullname.setText(dataSnapshot.child(signup_username).child("name").getValue().toString());
                    email.setText(signup_email);
                    college.setText(dataSnapshot.child(signup_username).child("college").getValue().toString());
                    mobile.setText(dataSnapshot.child(signup_username).child("mobile").getValue().toString());
                    qualification.setText(dataSnapshot.child(signup_username).child("qualification").getValue().toString());
                    String gender_s = dataSnapshot.child(signup_username).child("qualification").getValue().toString();
                    if(gender_s.equals("Male"))
                    {
                        male.setChecked(true);
                    }
                    else if(gender_s.equals("Female"))
                    {
                        female.setChecked(true);
                    }
                    if(gender_s.equals("Other"))
                    {
                        others.setChecked(true);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    public void launch_my_patients(String doc_username) {

        Intent i =new Intent(this, My_patients.class);
        i.putExtra("username",doc_username);
        //i.putExtra("username",doc_username);done
        startActivity(i);
    }

    public void launch_signup()
    {
        Intent i =new Intent(this, Signup.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver  , new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    Snackbar sb = null;
    private BroadcastReceiver networkStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            boolean isConnected = ni != null &&
                    ni.isConnectedOrConnecting();


            if (isConnected) {
                try{
                    sb.dismiss();
                }
                catch (Exception ex)
                {
                    Log.e("Exception", ex.getStackTrace().toString());
                }
            } else {
                sb = Snackbar.make(findViewById(R.id.doctor_profile_ui), "No Internet Connection",Snackbar.LENGTH_INDEFINITE);
                sb.setAction("Start Wifi", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);
                    }
                }).setActionTextColor(getResources().getColor(R.color.holo_blue_light));
                sb.show();
            }
        }
    };
}

