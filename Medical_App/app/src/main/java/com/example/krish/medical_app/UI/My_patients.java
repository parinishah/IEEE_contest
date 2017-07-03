package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

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
import java.util.Calendar;

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
    protected DrawerLayout drawerLayout;
    protected TextView logout_btn;
    protected TextView profile_btn;

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
        logout_btn = (TextView) findViewById(R.id.logout_btn);
        profile_btn = (TextView) findViewById(R.id.pro_btn);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);

        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patient_id = existing_patients.child(doc_username).child("patients:").push().getKey();
                launch_new_patient_info(doc_username,patient_id);
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getSharedPreferences("doctor_username", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("doctor_username",null);
                editor.commit();
                launch_login();
            }
        });


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

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

        existing_patients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            patientadapter.clear();

                for(DataSnapshot postSnapshot:dataSnapshot.child(doc_username).child("patients").getChildren()){
                    String name = postSnapshot.child("patient_first_name").getValue().toString();
                    String lname = postSnapshot.child("patient_last_name").getValue().toString();
                    String dob = postSnapshot.child("patient_dob").getValue().toString();
                    String age= getAge(dob);
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

    public void launch_login()
    {
        startActivity(new Intent(this,Login.class));
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public String getAge(String v_dob)
    {
        String s1, s2, s3;
        s1 = ""+ v_dob.charAt(0) + v_dob.charAt(1);
        s2 = "" + v_dob.charAt(3) + v_dob.charAt(4);
        s3 = "" + v_dob.charAt(6) + v_dob.charAt(7) + v_dob.charAt(8) + v_dob.charAt(9);

        int day,month,year;

        day = Integer.parseInt(s1);
        month = Integer.parseInt(s2);
        year = Integer.parseInt(s3);

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
}
