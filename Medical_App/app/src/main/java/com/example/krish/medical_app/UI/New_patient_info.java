package com.example.krish.medical_app.UI;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Patient;
import com.example.krish.medical_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by parini on 08-06-2017.
 */

public class New_patient_info extends AppCompatActivity
{
    protected EditText firstname;
    protected EditText middlename;
    protected EditText lastname;
    protected TextView patient_id;
    protected RadioButton male;
    protected RadioButton female;
    protected RadioButton other;
    protected TextView dob;
    protected ImageButton calendar;
    protected EditText age;
    protected EditText email;
    protected EditText address;
    protected EditText mobile_num;
    protected EditText phone_num;
    protected EditText diagnosis;
    protected EditText medical_history;
    protected TextView create;
    protected TextView cancel;
    protected String doc_username,pat_id;
    protected Patient patient_obj;

    protected DatePicker datepicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_patient_info);


        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");
        pat_id = bundle.getString("patient_id");

        firstname = (EditText)findViewById(R.id.editText_new_first);
        middlename = (EditText)findViewById(R.id.editText_new_middle);
        lastname = (EditText)findViewById(R.id.editText_new_last);
        patient_id = (TextView)findViewById(R.id.Textview_new_id);
        dob = (TextView) findViewById(R.id.textView_new_dob);
        calendar = (ImageButton) findViewById(R.id.imageButton_new_calendar);
        age = (EditText)findViewById(R.id.editText_new_age);
        email = (EditText)findViewById(R.id.editText_new_email);
        address = (EditText)findViewById(R.id.editText_new_address);
        mobile_num = (EditText)findViewById(R.id.editText_new_mobile);
        phone_num = (EditText)findViewById(R.id.editText_new_phone);
        diagnosis = (EditText)findViewById(R.id.editText_new_diagnosis_type_1);
        medical_history = (EditText)findViewById(R.id.editText_new_add_medical_history);
        male = (RadioButton)findViewById(R.id.radio_new_male);
        female = (RadioButton)findViewById(R.id.radio_new_female);
        other = (RadioButton)findViewById(R.id.radio_new_other);
        create = (TextView)findViewById(R.id.textView_new_create);
        cancel = (TextView)findViewById(R.id.textView_new_cancel_btn);

        patient_id.setText(pat_id);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(New_patient_info.this);
                dialog.setContentView(R.layout.datepicker);
                dialog.setTitle("Pick a date");

                // set the custom dialog components - text, image and button

                final DatePicker datepick= (DatePicker)dialog.findViewById(R.id.calendarView);
                Button select = (Button) dialog.findViewById(R.id.select);


                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int datei,month,year;
                        String  dmy;
                        datei = datepick.getDayOfMonth();

                        month = datepick.getMonth()+1;
                        year= datepick.getYear();
                        if(month<10 && datei<10) {
                            dmy = "0" + datei + "-0" + month + "-" + year ;
                        }
                        else if(month<10){
                            dmy = "" + datei + "-0" + month + "-" + year ;
                        }
                        else if(datei<10){
                            dmy = "0" + datei + "-" + month + "-" + year ;
                        }
                        else{
                            dmy = "" + datei + "-" + month + "-" + year ;
                        }


                        dialog.dismiss();
                        dob.setText(dmy);
                        String s_age = getAge(year,month,datei);
                        if(s_age.compareTo("NA")==0)
                        {
                            dob.setText("");
                            dob.setHintTextColor(Color.RED);
                            dob.setHint("Enter Valid DOB");
                        }
                        else
                        {
                            dob.setText(dmy);
                            age.setText(s_age);
                        }


                    }
                });

                dialog.show();

            }

        });



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_fname, s_dob,s_mobile,s_diagnosis,s_address,s_gender;
                s_fname = firstname.getText().toString();
                s_dob = dob.getText().toString();
                s_mobile = mobile_num.getText().toString();
                s_diagnosis = diagnosis.getText().toString();
                s_address = address.getText().toString();

                if(male.isChecked())
                {
                    s_gender = "Male";
                }
                else if(female.isChecked())
                {
                    s_gender = "Female";
                }
                else if(other.isChecked())
                {
                    s_gender = "Other";
                }
                else
                {
                    s_gender = "NA";
                }

                if(s_fname.length()==0)
                {
                    firstname.setHintTextColor(Color.RED);
                    firstname.setHint("Required Firstname");
                }
                else if(s_dob.length()==0)
                {
                    dob.setTextColor(Color.RED);
                    dob.setText("Required DOB");
                }
                else if(s_address.length()==0)
                {
                    address.setHintTextColor(Color.RED);
                    address.setHint("Required Address");
                }
                else if(s_mobile.length()==0)
                {
                    mobile_num.setHintTextColor(Color.RED);
                    mobile_num.setHint("Required Mobile No.");
                }
                else if(s_diagnosis.length()==0)
                {
                    diagnosis.setHintTextColor(Color.RED);
                    diagnosis.setHint("Required Diagnosis");
                }

                else
                {
                    patient_obj = new Patient(pat_id,s_fname,middlename.getText().toString(),lastname.getText().toString(),s_gender,s_dob,age.getText().toString(),
                            email.getText().toString(),s_address,s_mobile,phone_num.getText().toString(),s_diagnosis,medical_history.getText().toString());
                    patient_obj.firebase_connect(doc_username);
                    launch_My_patients(doc_username);
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_My_patients(doc_username);
            }
        });



    }
    public void launch_My_patients(String doc_username)
    {
        Intent i = new Intent(this,My_patients.class);
        i.putExtra("username",doc_username);
        startActivity(i);
    }

    public String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
        }

        String ageS;
        if(age<0) {
            ageS = "NA";

        }
        else
        {
            Integer ageInt = new Integer(age);
            ageS = ageInt.toString();
        }

        return ageS;
    }

    @Override
    public void onBackPressed() {
    }
}
