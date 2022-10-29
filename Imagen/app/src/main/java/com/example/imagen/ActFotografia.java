package com.example.imagen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActFotografia extends AppCompatActivity {

    private ImageView img;
    ActivityResultLauncher<String> mTakePhoto;  // cgl
    String rutaimagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_fotografia);
        img= (ImageView) findViewById(R.id.id_foto);

        /*if (ContextCompat.checkSelfPermission(ActFotografia.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActFotografia.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActFotografia.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        } */

    }
    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "foto_" + timeStamp + "_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imagen = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                directorio      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        rutaimagen = imagen.getAbsolutePath();

        String variable = "null";



        return imagen;
    }

    static final int REQUEST_IMAGE_CAPTURE=1;
    public void tomarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if (intent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.e("Error x", ex.toString());
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Toast.makeText(this, "Opcion 444", Toast.LENGTH_SHORT).show();
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }


        //}
    }

    @Override  // método para mostrar la imagen tomada en la cámara.
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            //  Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap imageBitmap = BitmapFactory.decodeFile(rutaimagen); // una imagen almacenada
            img.setImageBitmap(imageBitmap);
        }
    }



}