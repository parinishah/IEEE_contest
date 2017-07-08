package com.example.krish.medical_app.UI;

/**
 * Created by KRISH on 08-07-2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krish.medical_app.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Printable extends AppCompatActivity implements View.OnClickListener
{

    protected TextView create;
    protected ImageView back;
    protected String doc_username, pat_id;
    LinearLayout pdflayout;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printable);
        Bundle bundle = getIntent().getExtras();
        doc_username = bundle.getString("username");
        pat_id = bundle.getString("patient_id");
        init();
        fn_permission();
        listener();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_view_patients(doc_username, pat_id);
            }
        });
    }


    private void init()
    {

        create = (TextView)findViewById(R.id.textView_printable_create_pdf);
        pdflayout = (LinearLayout) findViewById(R.id.linearLayout_printable);
        back = (ImageView) findViewById(R.id.imageView_print_back);

    }

    private void listener(){
        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textView_printable_create_pdf:

                if (boolean_save) {
                    Intent intent = new Intent(getApplicationContext(), Pdf_view.class);
                    startActivity(intent);

                } else {
                    if (boolean_permission) {
                        progressDialog = new ProgressDialog(Printable.this);
                        progressDialog.setMessage("Please wait");
                        bitmap = loadBitmapFromView(pdflayout, pdflayout.getWidth(), pdflayout.getHeight());
                        createPdf();
//                        saveBitmap(bitmap);
                    } else {

                    }

                    createPdf();
                    break;
                }
        }
    }

    public void launch_view_patients(String doc_username, String pat_id)
    {
        Intent i = new Intent(this, View_patient.class);
        i.putExtra("username", doc_username);
        i.putExtra("patient_id",pat_id);
        startActivity(i);
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        canvas.drawPaint(paint);


        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        String targetPdf = "/sdcard/patient.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            boolean_save=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }



    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(Printable.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(Printable.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(Printable.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(Printable.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

}
