package com.teamihc.inventas.backend;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.teamihc.inventas.BuildConfig;
import com.teamihc.inventas.activities.MainActivity;

import org.sqldroid.SQLDroidBlob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase con variables y métodos misceláneos
 */
public class Herramientas
{
    public static final String FORMATO_FECHA_STRING = "yyyy/MM/dd";
    public static final String FORMATO_FECHA_FRONT_STRING = "dd/MM/yyyy";
    public static final String FORMATO_TIEMPO_STRING = "HH:mm:ss";
    public static final String FORMATO_TIEMPO_FRONT_STRING = "HH:mm";
    public static final String SIMBOLO_BS = "Bs.S";
    public static final String SIMBOLO_D  = "$";
    
    
    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING);
    public static final SimpleDateFormat FORMATO_FECHA_FRONT = new SimpleDateFormat(Herramientas.FORMATO_FECHA_FRONT_STRING);
    public static final SimpleDateFormat FORMATO_TIEMPO = new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING);
    public static final SimpleDateFormat FORMATO_TIEMPO_FRONT = new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_FRONT_STRING);
    public static final SimpleDateFormat FORMATO_FECHATIEMPO = new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING + " " +Herramientas.FORMATO_TIEMPO_STRING);
    public static final NumberFormat FOMATO_MONEDA = NumberFormat.getNumberInstance(new Locale("es","VE"));
    
    public static String formatearMonedaBs(float monto)
    {
        return SIMBOLO_BS + " " + FOMATO_MONEDA.format(Math.round(monto*100.0f)/100.0f);
    }
    
    public static String formatearMonedaDolar(float monto)
    {
        return SIMBOLO_D + " " + FOMATO_MONEDA.format(Math.round(monto*100.0f)/100.0f);
    }
    
    /**
     * Copia un archivo de la carpeta assets del apk a la carpeta /data/data del teléfono.
     * @param path ruta del archivo en assets.
     * @param assetManager asset manager del activity.
     */
    public static void copyFileOrDir(String path, AssetManager assetManager)
    {
        String assets[] = null;
        try
        {
            assets = assetManager.list(path);
            if (assets.length == 0)
            {
                copyFile(path, assetManager);
            } else
            {
                String fullPath = "/data/data/" + BuildConfig.APPLICATION_ID + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i)
                {
                    copyFileOrDir(path + "/" + assets[i], assetManager);
                }
            }
        }
        catch (IOException ex)
        {
            Log.e("tag", "I/O Exception", ex);
        }
    }
    private static void copyFile(String filename, AssetManager assetManager)
    {
        InputStream in = null;
        OutputStream out = null;
        try
        {
            String newFileName = "/data/data/" + BuildConfig.APPLICATION_ID + "/" + filename;
            
            in = assetManager.open(filename);
            out = new FileOutputStream(newFileName);
            
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        }
        catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }
        
    }

    //================================CAPTURAR FOTOS============================================

    static String  currentPhotoPath;

    private static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static final int REQUEST_PHOTO = 1;

    public static String imagenDesdeCamara(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "com.teamihc.inventas.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_PHOTO);

                return currentPhotoPath;
            }
        }

        return null;
    }

    public static String imagenDesdeGaleria(Activity activity){
        Intent selectPictureIntent = new Intent(Intent.ACTION_PICK);
        selectPictureIntent.setType("image/*");
        // Ensure that there's a camera activity to handle the intent
        if (selectPictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "com.teamihc.inventas.android.fileprovider",
                        photoFile);
                selectPictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(selectPictureIntent, REQUEST_PHOTO);
                //activity.startActivityForResult(Intent.createChooser(selectPictureIntent, "Elija una opcion"), IMAGES_CODE);
                return currentPhotoPath;
            }
        }

        return null;
    }

    //================================CONSULTAR FOTOS============================================

    public static Uri getImageUriFromPath(String photoPath) {
        File f = new File(photoPath);
        return Uri.fromFile(f);
    }

    public static Bitmap getCompresBitmapImage(View view, String photoPath) {
        // Get the dimensions of the View
        int targetW = view.getWidth();
        int targetH = view.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }
}
