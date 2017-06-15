package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 08-06-2017.
 */

public class View_patient extends AppCompatActivity
{

    protected ImageButton back;
    protected ImageButton more;
    protected TextView patient;
    protected TextView patient_name;
    protected TextView gender;
    protected TextView age;
    protected TextView dob_value;
    protected TextView id_value;
    protected TextView diagnosis_value;
    protected TextView mobile_value;
    protected TextView phone_value;
    protected TextView medical_history_value;
    protected ImageButton notes;
    protected ImageButton images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient);

        back = (ImageButton) findViewById(R.id.imageButton_view_back);
        more = (ImageButton) findViewById(R.id.imageButton_view_more);
        patient = (TextView) findViewById(R.id.textView_view_patient);
        patient_name = (TextView) findViewById(R.id.textView_view_patient_name);
        gender = (TextView) findViewById(R.id.textView_view_gender);
        age = (TextView) findViewById(R.id.textView_view_age);
        dob_value = (TextView) findViewById(R.id.textView_view_dob_value);
        id_value = (TextView) findViewById(R.id.textView_view_id_value);
        diagnosis_value = (TextView) findViewById(R.id.textView_view_diagnosis_value);
        mobile_value = (TextView) findViewById(R.id.textView_view_mobile_value);
        phone_value = (TextView) findViewById(R.id.textView_view_phone_value);
        medical_history_value = (TextView) findViewById(R.id.textView_view_medical_history_value);
        notes = (ImageButton)findViewById(R.id.imageButton_view_notes);
        images = (ImageButton)findViewById(R.id.imageButton_view_images);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_my_patients();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(View_patient.this, more);
                menu.getMenuInflater().inflate(R.menu.patient_profile_options, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });

                menu.show();
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_notes();
            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_pictures_options_popup();
            }
        });


    }

    public void launch_my_patients() { startActivity(new Intent(this, My_patients.class)); }

    public void launch_notes(){ startActivity(new Intent(this, Notes.class)); }

    public void launch_pictures_options_popup() { startActivity(new Intent(this, Pictures_options_popup.class)); }

    public void launch_new_patient_info() { startActivity(new Intent(this, New_patient_info.class)); }

    public void launch_delete_patient_popup() { startActivity(new Intent(this, Delete_patient_popup.class)); }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patient_profile_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.patient_profile_options_edit:
                launch_new_patient_info();
                return true;

            case R.id.patient_profile_options_save_printable_copy:

                return true;

            case R.id.patient_profile_options_delete:
                launch_delete_patient_popup();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed()
    {    }


}
