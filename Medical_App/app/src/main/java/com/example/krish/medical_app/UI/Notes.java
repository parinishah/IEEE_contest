package com.example.krish.medical_app.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 13-06-2017.
 */

public class Notes extends AppCompatActivity
{

    protected ImageButton delete;
    protected ImageButton save;
    protected EditText title;
    protected EditText date;
    protected EditText note;

    protected EditText medication;
    protected EditText dispense;
    protected EditText unit;
    protected EditText refills;
    protected EditText sig;
    protected String doc_username, pat_id,note_title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");
        pat_id = bundle.getString("patient_id");
        note_title = bundle.getString("note_title");

        delete = (ImageButton) findViewById(R.id.imageButton_notes_delete);
        save = (ImageButton) findViewById(R.id.imageButton_notes_save);
        title = (EditText) findViewById(R.id.editText_notes_title);
        date = (EditText) findViewById(R.id.editText_notes_date);
        note = (EditText) findViewById(R.id.editText_notes_note);

        medication = (EditText) findViewById(R.id.editText_prescription_medication);
        dispense = (EditText) findViewById(R.id.editText_prescription_dispense);
        unit = (EditText) findViewById(R.id.editText_prescription_unit);
        refills = (EditText) findViewById(R.id.editText_prescription_refills);
        sig = (EditText) findViewById(R.id.editText_prescription_sig);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogopener();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_view_patient();
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
                delete_note();
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

    public void launch_view_patient(){ startActivity(new Intent(this, View_patient.class));}



    public void delete_note()
    {
        //code to delete note
    }

    @Override
    public void onBackPressed() {
    }
}
