package com.example.krish.medical_app.UI;

import android.app.Dialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Note;
import com.example.krish.medical_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by KRISH on 13-06-2017.
 */

public class Notes extends AppCompatActivity
{

    protected ImageButton delete;
    protected ImageButton save;
    protected EditText title;
    protected TextView date;
    protected EditText note;
    protected EditText medication;
    protected EditText dispense;
    protected EditText unit;
    protected EditText refills;
    protected EditText sig;
    protected String doc_username, pat_id,note_id;
    protected DatabaseReference notes;
    protected Note note_obj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");
        pat_id = bundle.getString("patient_id");
        note_id = bundle.getString("note_id");

        notes = FirebaseDatabase.getInstance().getReference();

        delete = (ImageButton) findViewById(R.id.imageButton_notes_delete);
        save = (ImageButton) findViewById(R.id.imageButton_notes_save);
        title = (EditText) findViewById(R.id.editText_notes_title);
        date = (TextView) findViewById(R.id.editText_notes_date);
        note = (EditText) findViewById(R.id.editText_notes_note);
        medication = (EditText) findViewById(R.id.editText_prescription_medication);
        dispense = (EditText) findViewById(R.id.editText_prescription_dispense);
        unit = (EditText) findViewById(R.id.editText_prescription_unit);
        refills = (EditText) findViewById(R.id.editText_prescription_refills);
        sig = (EditText) findViewById(R.id.editText_prescription_sig);

        String date_s = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        date.setText(date_s);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogopener();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                note_obj = new  Note(note_id,title.getText().toString(),date.getText().toString(),note.getText().toString(),medication.getText().toString(),dispense.getText().toString(),unit.getText().toString(),refills.getText().toString(),sig.getText().toString());
                note_obj.firebase_note(doc_username,pat_id);
                launch_view_patient(doc_username,pat_id);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               if(dataSnapshot.child(doc_username).child("patients").child(pat_id).child("notes").child(note_id).exists())
               {
                   DataSnapshot d1 = dataSnapshot.child(doc_username).child("patients").child(pat_id).child("notes").child(note_id);
                   String n_date = d1.child("note_date").getValue().toString();
                   String n_title = d1.child("note_title").getValue().toString();
                   String n_note = d1.child("note_text").getValue().toString();
                   String n_medication = d1.child("note_medication").getValue().toString();
                   String n_dispense = d1.child("note_dispense").getValue().toString();
                   String n_unit = d1.child("note_unit").getValue().toString();
                   String n_refills = d1.child("note_refills").getValue().toString();
                   String n_sig = d1.child("note_sig").getValue().toString();

                   title.setText(n_title);
                   date.setText(n_date);
                   note.setText(n_note);
                   medication.setText(n_medication);
                   dispense.setText(n_dispense);
                   unit.setText(n_unit);
                   refills.setText(n_refills);
                   sig.setText(n_sig);


               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void dialogopener()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_popup);

        final TextView delete = (TextView)dialog.findViewById(R.id.textView_delete_delete);
        TextView cancel = (TextView)dialog.findViewById(R.id.textView_delete_cancel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_note(doc_username,pat_id);
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

    public void launch_view_patient(String doc_username, String pat_id){

        Intent i = new Intent(this, View_patient.class);
        i.putExtra("username", doc_username);
        i.putExtra("patient_id",pat_id);
        startActivity(i);
    }



    public void delete_note(String doc_username,String pat_id)
    {
        notes.child(doc_username).child("patients").child(pat_id).child("notes").child(note_id).removeValue();
        launch_view_patient(doc_username,pat_id);
    }

    @Override
    public void onBackPressed() {
        launch_view_patient(doc_username,pat_id);
    }
}
