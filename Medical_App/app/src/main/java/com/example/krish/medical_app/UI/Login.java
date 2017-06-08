package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krish.medical_app.R;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    protected EditText email_username;
    protected EditText password;
    protected TextView signup;
    protected TextView login;
    protected TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email_username = (EditText)findViewById(R.id.editText_login_email);
        password = (EditText) findViewById(R.id.editText_login_password);
        signup = (TextView) findViewById(R.id.textView_login_signup_btn);
        login = (TextView) findViewById(R.id.textView_login_login_btn);
        forgot_password = (TextView)findViewById(R.id.textView_login_forgot);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_signup();
            }
        });

    }
    public void launch_signup()
    {
        startActivity(new Intent(this,Signup.class));
    }
}
