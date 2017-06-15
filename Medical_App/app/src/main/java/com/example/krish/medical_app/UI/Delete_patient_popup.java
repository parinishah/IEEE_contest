package com.example.krish.medical_app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 13-06-2017.
 */

public class Delete_patient_popup extends AppCompatActivity {

    protected TextView delete;
    protected TextView cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_patient_popup);

        delete = (TextView) findViewById(R.id.textView_delete_patient_delete);
        cancel = (TextView) findViewById(R.id.textView_delete_patient_cancel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_view_patient();
            }
        });

    }

    public void launch_view_patient() { startActivity(new Intent(this, View_patient.class)); }
}
