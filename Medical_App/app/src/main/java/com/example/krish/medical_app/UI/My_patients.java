package com.example.krish.medical_app.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 13-06-2017.
 */

public class My_patients extends AppCompatActivity
{

    protected ImageButton options;
    protected ImageButton add_patient;
    protected ScrollView scrollView;
    protected SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_patients);

        options = (ImageButton) findViewById(R.id.imageButton_my_patients_options);
        add_patient = (ImageButton) findViewById(R.id.imageButton_my_patients_add_patient);
        scrollView = (ScrollView) findViewById(R.id.scrollView_my_patients);
        searchView = (SearchView) findViewById(R.id.search_bar_my_patients_search);

    }
}
