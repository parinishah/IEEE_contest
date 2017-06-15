package com.example.krish.medical_app.Java_classes;

/**
 * Created by parini on 16-06-2017.
 */

public class Notes
{
    private String notes_id;
    private String date;
    private String text;

    Notes(String notes_id, String date, String text)
    {
        this.notes_id = notes_id;
        this.date = date;
        this.text = text;
    }

    public String getNotes_id() {
        return notes_id;
    }

    public void setNotes_id(String notes_id) {
        this.notes_id = notes_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
