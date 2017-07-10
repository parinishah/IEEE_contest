package com.example.krish.medical_app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.krish.medical_app.Java_classes.Picture;
import com.example.krish.medical_app.R;
import com.example.krish.medical_app.UI.View_patient;

import java.util.List;

/**
 * Created by kishan on 08/07/2017.
 */

public class PictureAdapter extends ArrayAdapter<Picture>
{
    public PictureAdapter(Context context, List<Picture> pictures)
    {
        super(context,0, pictures);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Picture picture = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.photos_singleview, parent, false);

        ImageView img = (ImageView) rootView.findViewById(R.id.imgView);
        TextView date = (TextView)rootView.findViewById(R.id.textView_photos_singleview_date_value);

        date.setText(picture.getDate());
        Glide.with(getContext()).
                load(picture.getPath())
                .into(img);


        return rootView;
    }
}
