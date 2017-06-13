package com.example.krish.medical_app.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.krish.medical_app.R;

/**
 * Created by parini on 08-06-2017.
 */

public class New_patient extends AppCompatActivity
{
    protected EditText firstname;
    protected EditText middlename;
    protected EditText lastname;
    protected EditText patient_id;
    protected RadioButton male;
    protected RadioButton female;
    protected RadioButton other;
    protected EditText dob;
    protected EditText age;
    protected EditText email;
    protected EditText address;
    protected EditText mobile_num;
    protected EditText phone_num;
    protected EditText diagnosis;
    protected EditText medical_history;
    protected TextView create;
    protected TextView cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_patient_info);

        firstname = (EditText)findViewById(R.id.editText_new_first);
        middlename = (EditText)findViewById(R.id.editText_new_middle);
        lastname = (EditText)findViewById(R.id.editText_new_last);
        patient_id = (EditText)findViewById(R.id.editText_new_id);
        dob = (EditText)findViewById(R.id.editText_new_dob);
        age = (EditText)findViewById(R.id.editText_new_age);
        email = (EditText)findViewById(R.id.editText_new_email);
        address = (EditText)findViewById(R.id.editText_new_address);
        mobile_num = (EditText)findViewById(R.id.editText_new_mobile);
        phone_num = (EditText)findViewById(R.id.editText_new_phone);
        diagnosis = (EditText)findViewById(R.id.editText_new_diagnosis_type);
        medical_history = (EditText)findViewById(R.id.editText_new_add_medical_history);
        male = (RadioButton)findViewById(R.id.radio_new_male);
        female = (RadioButton)findViewById(R.id.radio_new_female);
        other = (RadioButton)findViewById(R.id.radio_new_other);
        create = (TextView)findViewById(R.id.textView_new_create);
        cancel = (TextView)findViewById(R.id.textView_new_cancel_btn);

    }
}
