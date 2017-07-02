package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krish.medical_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    protected EditText username;
    protected EditText password;
    protected TextView signup;
    protected TextView login;
    protected TextView forgot_password;
    protected String doctor_username;
    protected DatabaseReference Login ;
    protected String usernm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences sharedPref = getSharedPreferences("doctor_username", MODE_PRIVATE);
        doctor_username = sharedPref.getString("doctor_username", null);
        if (doctor_username != null)
        {
            launch_my_patients(doctor_username);
        }

        Login = FirebaseDatabase.getInstance().getReference();

        username = (EditText) findViewById(R.id.editText_login_email);
        password = (EditText) findViewById(R.id.editText_login_password);
        signup = (TextView) findViewById(R.id.textView_login_signup_btn);
        login = (TextView) findViewById(R.id.textView_login_login_btn);
        forgot_password = (TextView) findViewById(R.id.textView_login_forgot);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_signup();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if((dataSnapshot.child(username.getText().toString()).exists()) )
                        {
                            usernm = username.getText().toString();
                            if(!usernm.equals(null))
                            {
                                if(dataSnapshot.child(usernm).child("password").getValue().toString().equals(password.getText().toString()))
                                {
                                    SharedPreferences sharedPref = getSharedPreferences("doctor_username", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("doctor_username",usernm);
                                    editor.commit();
                                    launch_my_patients(usernm);
                                }
                                else
                                {
                                    password.setText("");
                                    password.setHint("Incorrect Password");

                                }
                            }

                        }
                        else
                        {
                            username.setText("");
                            password.setText("");
                            username.setHint("incorrect username!");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    public void launch_signup() {
        startActivity(new Intent(this, Signup.class));
    }


    public void launch_my_patients(String doc_username) {

        Intent i =new Intent(this, My_patients.class);
        i.putExtra("username",doc_username);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
