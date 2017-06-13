package com.example.krish.medical_app.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.krish.medical_app.R;

/**
 * Created by KRISH on 13-06-2017.
 */

public class Pictures_options_popup extends AppCompatActivity
{

    protected TextView gallery;
    protected TextView camera;
    protected TextView cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_patient_info);

        gallery = (TextView) findViewById(R.id.textView_pictures_options_gallery);
        camera = (TextView) findViewById(R.id.textView_pictures_options_camera);
        cancel = (TextView) findViewById(R.id.textView_pictures_options_cancel);

    }
}
