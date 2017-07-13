package com.raval.kishan.storagetest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    View imageContainer;
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.img);

        Glide.with(this).
                load("https://firebasestorage.googleapis.com/v0/b/storagetest-251ca.appspot.com/o/fire%2F74339e70-8751-4e01-83cd-06995d058246.png?alt=media&token=e58fe528-1994-4daa-b9e3-8d76d644e432")
                .into(imageView);



        /*BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageView.setDrawingCacheEnabled(false);
        byte[] data = baos.toByteArray();

        String path = "fire/" + UUID.randomUUID() + ".png";
        StorageReference fireRef = storage.getReference(path);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("text", "temporary text").build();

        UploadTask uploadTask = fireRef.putBytes(data, metadata);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("Upload", "success");

                Uri url = taskSnapshot.getDownloadUrl();
                String u = url.toString();
                Log.i("Link is ",u);

                //imageView.setImageResource();
            }
        });*/
    }
}
