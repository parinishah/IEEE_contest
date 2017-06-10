package com.example.krish.medical_app.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 08-06-2017.
 */

public class Prescription extends AppCompatActivity
{

    protected TextView cancel;
    protected TextView save;
    protected EditText medication;
    protected EditText dispense;
    protected EditText unit;
    protected EditText refills;
    protected EditText sig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);

        cancel = (TextView) findViewById(R.id.textView_prescription_cancel);
        save = (TextView) findViewById(R.id.textView_prescription_save);
        medication = (EditText) findViewById(R.id.editText_prescription_medication);
        dispense = (EditText) findViewById(R.id.editText_prescription_dispense);
        unit = (EditText) findViewById(R.id.editText_prescription_unit);
        refills = (EditText) findViewById(R.id.editText_prescription_refills);
        sig = (EditText) findViewById(R.id.editText_prescription_sig);

    }
}
