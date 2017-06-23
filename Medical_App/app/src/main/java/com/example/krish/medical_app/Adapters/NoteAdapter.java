package com.example.krish.medical_app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.krish.medical_app.Java_classes.Note;
import com.example.krish.medical_app.R;

import java.util.List;

/**
 * Created by parini on 23-06-2017.
 */

public class NoteAdapter extends ArrayAdapter<Note>
{
    public NoteAdapter(Context context, List<Note> notes)
    {
        super(context,0,notes);
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Note note = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.notes_singleview, parent, false);

        TextView title = (TextView)rootView.findViewById(R.id.textView_notes_singleview_title);
        TextView date = (TextView)rootView.findViewById(R.id.textView_notes_singleview_date_value);

        title.setText(note.getNotes_id());
        date.setText(note.getDate());

        return rootView;
    }
}
