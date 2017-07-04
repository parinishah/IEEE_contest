package com.example.krish.medical_app.UI;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.krish.medical_app.Adapters.NoteAdapter;
import com.example.krish.medical_app.Java_classes.Note;
import com.example.krish.medical_app.Manifest;
import com.example.krish.medical_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.master.permissionhelper.PermissionHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by KRISH on 08-06-2017.
 */

public class View_patient extends AppCompatActivity {

    protected ImageButton back;
    protected ImageButton more;
    protected TextView patient;
    protected TextView patient_name;
    protected TextView gender;
    protected TextView age;
    protected TextView dob_value;
    protected TextView id_value;
    protected TextView diagnosis_value;
    protected TextView mobile_value;
    protected TextView phone_value;
    protected TextView medical_history_value;
    protected ImageButton notes;
    protected ImageButton images;
    protected String doc_username,pat_id;
    protected DatabaseReference view_patient,note_ref;
    protected Note note;
    protected ArrayList<Note> note_array;
    protected NoteAdapter noteadapter;
    protected ListView note_list;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int REQUEST_IMAGE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient);

        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");
        pat_id= bundle.getString("patient_id");

        note_array = new ArrayList<>();

        view_patient = FirebaseDatabase.getInstance().getReference();
        note_ref = view_patient.child(doc_username).child("patients").child(pat_id).child("notes");

        back = (ImageButton) findViewById(R.id.imageButton_view_back);
        more = (ImageButton) findViewById(R.id.imageButton_view_more);
        patient = (TextView) findViewById(R.id.textView_view_patient);
        patient_name = (TextView) findViewById(R.id.textView_view_patient_name);
        gender = (TextView) findViewById(R.id.textView_view_gender);
        age = (TextView) findViewById(R.id.textView_view_age);
        dob_value = (TextView) findViewById(R.id.textView_view_dob_value);
        id_value = (TextView) findViewById(R.id.textView_view_id_value);
        diagnosis_value = (TextView) findViewById(R.id.textView_view_diagnosis_value);
        mobile_value = (TextView) findViewById(R.id.textView_view_mobile_value);
        phone_value = (TextView) findViewById(R.id.textView_view_phone_value);
        medical_history_value = (TextView) findViewById(R.id.textView_view_medical_history_value);
        notes = (ImageButton) findViewById(R.id.imageButton_view_notes);
        images = (ImageButton) findViewById(R.id.imageButton_view_images);


        noteadapter = new NoteAdapter(getApplicationContext(), note_array);
        note_list = (ListView)findViewById(R.id.listView_notes);
        note_list.setAdapter(noteadapter);

        note_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Note n = note_array.get(i);
                Intent in = new Intent(View_patient.this, Notes.class);
                in.putExtra("patient_id",pat_id);
                in.putExtra("username",doc_username);
                in.putExtra("note_id",n.getNotes_id());
                startActivity(in);
            }
        });

        note_list.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
// Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
// Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
// Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_my_patients(doc_username);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(View_patient.this, more);
                menu.getMenuInflater().inflate(R.menu.patient_profile_options, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.patient_profile_options_edit:
                                launch_new_patient_info(doc_username,pat_id);
                                return true;

                            case R.id.patient_profile_options_save_printable_copy:

                                return true;

                            case R.id.patient_profile_options_delete:
                                dialogopener();
                                return true;

                            default:
                                return true;
                        }

                    }
                });

                menu.show();
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_notes(doc_username,pat_id);
            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(isReadAllowedofCamera())&&!(isReadAllowedofRead())&&!(isReadAllowedofWrite())){
                    request(0);
                }
                else if (!(isReadAllowedofCamera())){
                    request(1);
                }
                else if(!(isReadAllowedofRead())&&!(isReadAllowedofWrite())){
                    request(2);
                }
                dialogopener_images();
            }
        });

    }

    private boolean isReadAllowedofCamera() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(View_patient.this, android.Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private boolean isReadAllowedofRead() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(View_patient.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private boolean isReadAllowedofWrite() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(View_patient.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void request(int id){
        if(id ==0){
            PermissionHelper permissionHelper = new PermissionHelper(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            permissionHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {

                }

                @Override
                public void onPermissionDenied() {


                }

                @Override
                public void onPermissionDeniedBySystem() {

                }
            });
        }
        else if(id ==1){
            PermissionHelper permissionHelper = new PermissionHelper(this, new String[]{android.Manifest.permission.CAMERA}, 100);
            permissionHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {

                }

                @Override
                public void onPermissionDenied() {


                }

                @Override
                public void onPermissionDeniedBySystem() {

                }
            });
        }
        else if(id ==2){
            PermissionHelper permissionHelper = new PermissionHelper(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            permissionHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {

                }

                @Override
                public void onPermissionDenied() {


                }

                @Override
                public void onPermissionDeniedBySystem() {

                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        view_patient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot d1 = dataSnapshot.child(doc_username).child("patients").child(pat_id);
                if(d1.exists())
                {
                    String v_name,v_gender,v_dob,v_diagnosis,v_mobile,v_phone,v_medhis;
                    v_name = d1.child("patient_first_name").getValue().toString() +" "+ d1.child("patient_last_name").getValue().toString();
                    v_gender = d1.child("patient_gender").getValue().toString();
                    v_dob = d1.child("patient_dob").getValue().toString();
                    v_diagnosis = d1.child("patient_diagnosis").getValue().toString();
                    v_mobile = d1.child("patient_mobile").getValue().toString();
                    v_phone = d1.child("patient_phone").getValue().toString();
                    v_medhis = d1.child("patient_medical_history").getValue().toString();

                    patient.setText(v_name.toUpperCase());
                    patient_name.setText(v_name);
                    gender.setText(v_gender);
                    age.setText(getAge(v_dob));
                    dob_value.setText(v_dob);
                    id_value.setText(pat_id);
                    diagnosis_value.setText(v_diagnosis);
                    mobile_value.setText(v_mobile);
                    phone_value.setText(v_phone);
                    medical_history_value.setText(v_medhis);

                    noteadapter.clear();

                    for(DataSnapshot postSnapshot:dataSnapshot.child(doc_username).child("patients").child(pat_id).child("notes").getChildren()) {
                        String date = postSnapshot.child("note_date").getValue().toString();
                        String title = postSnapshot.child("note_title").getValue().toString();
                        String id = postSnapshot.getKey();
                        note = new Note(id,title,date,null,null,null,null,null,null);
                        note_array.add(note);
                        noteadapter.notifyDataSetChanged();
                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void launch_my_patients(String doc_username) {

        Intent i = new Intent(this, My_patients.class);
        i.putExtra("username",doc_username);
        startActivity(i);
    }

    public void launch_notes(String doc_username,String pat_id) {

        Intent i = new Intent(this, Notes.class);
        i.putExtra("username",doc_username);
        i.putExtra("patient_id",pat_id);
        String notes_id = note_ref.push().getKey().toString();
        i.putExtra("note_id",notes_id);
        startActivity(i);
    }
    ImageView imageView,imageView1;
    public void dialogopener_images()
    {
        final Dialog dialog = new Dialog(View_patient.this);
        dialog.setContentView(R.layout.pictures_options_popup);

        final TextView gallery_btn = (TextView) dialog.findViewById(R.id.textView_pictures_options_gallery);
        TextView camera_btn = (TextView) dialog.findViewById(R.id.textView_pictures_options_camera);
        TextView cancel_btn = (TextView) dialog.findViewById(R.id.textView_pictures_options_cancel);
        imageView = (ImageView) dialog.findViewById(R.id.imgView);
        imageView1 = (ImageView) dialog.findViewById(R.id.imgView1);

        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

        else if (requestCode == REQUEST_IMAGE_CAPTURE)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
            imageView1.setImageBitmap(photo);
        }

    }

    public void launch_new_patient_info(String doc_username,String pat_id) {

        Intent i = new Intent(this, New_patient_info.class);
        i.putExtra("username",doc_username);
        i.putExtra("patient_id",pat_id);
        startActivity(i);
    }

    public void dialogopener()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_popup);

        final TextView delete = (TextView) dialog.findViewById(R.id.textView_delete_delete);
        TextView cancel = (TextView) dialog.findViewById(R.id.textView_delete_cancel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_patient(doc_username,pat_id);
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

    public void delete_patient(String doc_username,String pat_id)
    {
        view_patient.child(doc_username).child("patients").child(pat_id).removeValue();
        launch_my_patients(doc_username);
    }
    public void onBackPressed()
    {    }

    public String getAge(String v_dob)
    {
        String[] temp = v_dob.split("-");
        int year, month, day;

        day = Integer.parseInt(temp[0]);
        month = Integer.parseInt(temp[1]);
        year = Integer.parseInt(temp[2]);

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        String ageS;
        if(age<0) {
            ageS = "NA";

        }
        else
        {
            Integer ageInt = new Integer(age);
            ageS = ageInt.toString();
        }

        return ageS;
    }

}
