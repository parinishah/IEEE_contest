package com.example.krish.medical_app.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Patient;
import com.example.krish.medical_app.R;

import java.util.List;

/**
 * Created by parini on 20-06-2017.
 */

public class PatientAdapter extends ArrayAdapter<Patient>
{

    public PatientAdapter(Context context, List<Patient> patients)
    {
        super(context,0, patients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Patient patient = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.my_patient_singleview, parent, false);

        TextView patient_id = (TextView)rootView.findViewById(R.id.textView_my_patient_view_id);
        TextView patient_gender = (TextView)rootView.findViewById(R.id.textView_my_patient_view_gender);
        TextView patient_age = (TextView)rootView.findViewById(R.id.textView_my_patient_view_age);
        TextView patient_name = (TextView)rootView.findViewById(R.id.textView_my_patient_view_name);
        ImageView gender = (ImageView) rootView.findViewById(R.id.imageView_my_patient_view_gender);

        patient_id.setText(patient.getPatient_id());
        patient_gender.setText(patient.getGender());
        patient_age.setText(patient.getAge());
        patient_name.setText(patient.getFirst_name());
        if(patient.getGender().equals("male"))
        {
           gender.setImageResource(R.drawable.male_gender);
        }
        else if(patient.getGender().equals("female"))
        {
            gender.setImageResource(R.drawable.female_gender);
        }
        else if(patient.getGender().equals("other"))
        {
            gender.setImageResource(R.drawable.other_gender);
        }



        return rootView;

    }
}
