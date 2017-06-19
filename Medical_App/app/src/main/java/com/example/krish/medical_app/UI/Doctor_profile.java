package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Doctor;
import com.example.krish.medical_app.R;

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
    protected TextView save;
    protected Doctor doctor_obj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile);

        final Bundle bundle = getIntent().getExtras();
        fullname = (EditText)findViewById(R.id.editText_doctor_fullname);
        email = (EditText)findViewById(R.id.editText_doctor_email);
        mobile = (EditText)findViewById(R.id.editText_doctor_mobile);
        qualification = (EditText)findViewById(R.id.editText_doctor_qualification);
        male = (RadioButton)findViewById(R.id.radio_doctor_male);
        female = (RadioButton)findViewById(R.id.radio_doctor_female);
        others = (RadioButton)findViewById(R.id.radio_doctor_other);
        back = (ImageButton) findViewById(R.id.imageButton_doctor_back);
        save = (TextView) findViewById(R.id.textView_doctor_save);


        email.setText(bundle.getString("email"));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_my_patients();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Gender = "NA";
                if (male.isChecked())
                {
                    Gender = "male";
                }
                else if(female.isChecked())
                {
                    Gender = "female";
                }
                else if(others.isChecked())
                {
                    Gender = "other";
                }

                doctor_obj = new Doctor(bundle.getString("username"),bundle.getString("password"),bundle.getString("email"),
                        fullname.getText().toString(),Gender,mobile.getText().toString(),qualification.getText().toString());
                doctor_obj.firebase_doctor();
                launch_my_patients();
            }
        });

    }

    public void launch_my_patients(){ startActivity(new Intent(this, My_patients.class)); }

    @Override
    public void onBackPressed() {
       
    }
}
