package com.example.krish.medical_app.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.krish.medical_app.MainActivity;
import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 08-06-2017.
 */

public class View_patient extends AppCompatActivity
{

    ImageButton more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient);

        more = (ImageButton) findViewById(R.id.imageButton_view_more);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patient_profile_options, menu);
        return true;
    }

}
