package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krish.medical_app.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

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
                launch_my_patients();
            }
        });
    }

    public void launch_login()
    {
        startActivity(new Intent(this, Login.class));
    }
    public void launch_my_patients()
    {
        startActivity(new Intent(this,My_patients.class));
    }

    @Override
    public void onBackPressed() {
    }
}

