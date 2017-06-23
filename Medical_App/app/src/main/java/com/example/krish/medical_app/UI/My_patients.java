package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.krish.medical_app.Adapters.PatientAdapter;
import com.example.krish.medical_app.Java_classes.Patient;
import com.example.krish.medical_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Created by KRISH on 13-06-2017.
 */

public class My_patients extends AppCompatActivity
{

    protected ImageButton options;
    protected ImageButton add_patient;
    protected ListView listView;
    protected String doc_username;
    protected PatientAdapter patientadapter;
    protected DatabaseReference existing_patients;
    protected Patient patient;
    protected ListView patient_list;
    protected String patient_id;

    ArrayList<Patient> patient_array;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_patients);

        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");

        existing_patients = FirebaseDatabase.getInstance().getReference();

        patient_array = new ArrayList<>();

        options = (ImageButton) findViewById(R.id.imageButton_my_patients_options);
        add_patient = (ImageButton) findViewById(R.id.imageButton_my_patients_add_patient);
        listView = (ListView) findViewById(R.id.listView_my_patients);

        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patient_id = existing_patients.child(doc_username).child("patients:").push().getKey();
                launch_new_patient_info(doc_username,patient_id);
            }
        });

        patientadapter = new PatientAdapter(getApplicationContext(), patient_array);
        patient_list = (ListView)findViewById(R.id.listView_my_patients);
        patient_list.setAdapter(patientadapter);

        patient_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Patient p = patient_array.get(i);
                Intent in = new Intent(My_patients.this, View_patient.class);
                in.putExtra("patient_id",p.getPatient_id());
                in.putExtra("username",doc_username);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        existing_patients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                patientadapter.clear();

                for(DataSnapshot postSnapshot:dataSnapshot.child(doc_username).child("patients").getChildren()){
                    String name = postSnapshot.child("patient_first_name").getValue().toString();
                    String lname = postSnapshot.child("patient_last_name").getValue().toString();
                    String age= postSnapshot.child("patient_age").getValue().toString();
                    String gender= postSnapshot.child("patient_gender").getValue().toString();
                    String id= postSnapshot.getKey().toString();
                    patient = new Patient(id,name,null,lname,gender,null,age,null,null,null,null,null,null);
                    patient_array.add(patient);
                    patientadapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void launch_new_patient_info(String doc_username,String patient_id){
        Intent i = new Intent(this, New_patient_info.class);
        i.putExtra("username",doc_username);
        i.putExtra("patient_id",patient_id);
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
