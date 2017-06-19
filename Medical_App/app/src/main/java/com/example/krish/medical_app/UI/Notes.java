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
    protected ImageButton add_prescription_icon;
    protected TextView add_prescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        delete = (ImageButton) findViewById(R.id.imageButton_notes_delete);
        save = (ImageButton) findViewById(R.id.imageButton_notes_save);
        title = (EditText) findViewById(R.id.editText_notes_title);
        date = (EditText) findViewById(R.id.editText_notes_date);
        note = (EditText) findViewById(R.id.editText_notes_note);
        add_prescription_icon = (ImageButton) findViewById(R.id.imageButton_notes_add_prescription);
        add_prescription = (TextView) findViewById(R.id.textView_notes_add_prescription);

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

        add_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_prescription();
            }
        });

        add_prescription_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_prescription();
            }
        });

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

    public void launch_prescription(){ startActivity(new Intent(this, Prescription.class));}

    public void delete_note()
    {
        //code to delete note
    }

    @Override
    public void onBackPressed() {
    }
}
