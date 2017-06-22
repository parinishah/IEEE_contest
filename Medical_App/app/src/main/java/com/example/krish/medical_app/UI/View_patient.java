package com.example.krish.medical_app.UI;

import android.app.Dialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by KRISH on 08-06-2017.
 */

public class View_patient extends AppCompatActivity {

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
    protected String doc_username,pat_id;
    protected DatabaseReference view_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient);

        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");
        pat_id= bundle.getString("patient_id");

        view_patient = FirebaseDatabase.getInstance().getReference();

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
        notes = (ImageButton) findViewById(R.id.imageButton_view_notes);
        images = (ImageButton) findViewById(R.id.imageButton_view_images);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_my_patients(doc_username);
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

        view_patient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot d1 = dataSnapshot.child(doc_username).child("patients").child(pat_id);
                String v_name,v_gender,v_age,v_dob,v_diagnosis,v_mobile,v_phone,v_medhis;
                v_name = d1.child("patient_first_name").getValue().toString() +" "+ d1.child("patient_last_name").getValue().toString();
                v_age = d1.child("patient_age").getValue().toString();
                v_gender = d1.child("patient_gender").getValue().toString();
                v_dob = d1.child("patient_dob").getValue().toString();
                v_diagnosis = d1.child("patient_diagnosis").getValue().toString();
                v_mobile = d1.child("patient_mobile").getValue().toString();
                v_phone = d1.child("patient_phone").getValue().toString();
                v_medhis = d1.child("patient_medical_history").getValue().toString();

                patient.setText(v_name.toUpperCase());
                patient_name.setText(v_name);
                gender.setText(v_gender);
                age.setText(v_age);
                dob_value.setText(v_dob);
                id_value.setText(pat_id);
                diagnosis_value.setText(v_diagnosis);
                mobile_value.setText(v_mobile);
                phone_value.setText(v_phone);
                medical_history_value.setText(v_medhis);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    public void launch_my_patients(String doc_username) {

        Intent i = new Intent(this, My_patients.class);
        i.putExtra("username",doc_username);
        startActivity(i);
    }

    public void launch_notes() {
        startActivity(new Intent(this, Notes.class));
    }

    public void launch_pictures_options_popup() {
        startActivity(new Intent(this, Pictures_options_popup.class));
    }

    public void launch_new_patient_info() {
        startActivity(new Intent(this, New_patient_info.class));
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
                dialogopener();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dialogopener()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_popup);

        final TextView delete = (TextView) findViewById(R.id.textView_delete_delete);
        TextView cancel = (TextView) findViewById(R.id.textView_delete_cancel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_patient();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void delete_patient()
    {
        //code to delete patient
    }
    public void onBackPressed()
    {    }


}
