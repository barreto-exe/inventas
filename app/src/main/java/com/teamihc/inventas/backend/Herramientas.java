package com.teamihc.inventas.backend;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.teamihc.inventas.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase con variables y métodos misceláneos
 */
public class Herramientas
{
    public static final String FORMATO_FECHA_STRING = "yyyy/MM/dd";
    public static final String FORMATO_FECHA_FRONT_STRING = "dd/MM/yyyy";
    public static final String FORMATO_TIEMPO_STRING = "HH:mm:ss";
    public static final String FORMATO_TIEMPO_FRONT_STRING = "hh:mm a";
    public static final String FOMATO_MONEDA_STRING = "###,###,###,##0.00";
    public static final String FOMATO_PORCENTAJE_STRING = "#0.00%";
    public static final String SIMBOLO_BS = "Bs.S";
    public static final String SIMBOLO_D = "$";
    
    public static SimpleDateFormat FORMATO_FECHA;
    public static SimpleDateFormat FORMATO_FECHA_FRONT;
    public static SimpleDateFormat FORMATO_TIEMPO;
    public static SimpleDateFormat FORMATO_TIEMPO_FRONT;
    public static SimpleDateFormat FORMATO_FECHATIEMPO;
    
    private static DecimalFormatSymbols simbolos;
    public static DecimalFormat FOMATO_MONEDA;
    public static DecimalFormat FOMATO_PORCENTAJE;
    
    public static void inicializarFormatos()
    {
        FORMATO_FECHA = new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING);
        FORMATO_FECHA_FRONT = new SimpleDateFormat(Herramientas.FORMATO_FECHA_FRONT_STRING);
        FORMATO_TIEMPO = new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING);
        FORMATO_TIEMPO_FRONT = new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_FRONT_STRING);
        FORMATO_FECHATIEMPO = new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING + " " + Herramientas.FORMATO_TIEMPO_STRING);
        
        simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        
        FOMATO_MONEDA = new DecimalFormat(FOMATO_MONEDA_STRING, simbolos);
        FOMATO_PORCENTAJE = new DecimalFormat(FOMATO_PORCENTAJE_STRING, simbolos);
    }
    
    public static String formatearDiaFecha(Date fecha)
    {
        String dia = new SimpleDateFormat("EEEE").format(fecha);
        return dia.substring(0,1).toUpperCase() + dia.substring(1) + ", " + Herramientas.FORMATO_FECHA_FRONT.format(fecha);
    }
    
    public static String formatearMoneda(float monto)
    {
        if (monto == 0)
            return "0";
        return FOMATO_MONEDA.format(monto);
    }
    
    public static String formatearMonedaBs(float monto)
    {
        if (monto == 0)
            return SIMBOLO_BS + " 0";
        return SIMBOLO_BS + " " + FOMATO_MONEDA.format(monto);
    }
    
    public static String formatearMonedaDolar(float monto)
    {
        if (monto == 0)
            return SIMBOLO_D + " 0";
        return SIMBOLO_D + " " + FOMATO_MONEDA.format(monto);
    }
    
    public static String formatearPorcentaje(float porcentaje)
    {
        if (porcentaje == 0)
            return "0%";
        return Herramientas.FOMATO_PORCENTAJE.format(porcentaje / 100);
    }
    
    /**
     * Copia un archivo de la carpeta assets del apk a la carpeta /data/data del teléfono.
     *
     * @param path         ruta del archivo en assets.
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
            }
            else
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
    
    
    public static File createImageFile(Activity activity) throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }
    
    public static final int PICTURE_FROM_CAMERA = 0;
    public static final int PICTURE_FROM_GALLERY = 1;
    private static String imagen_path = "";
    
    public static void imagenDesdeCamara(Activity activity)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile(activity);
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "com.teamihc.inventas.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, PICTURE_FROM_CAMERA);
                imagen_path = photoFile.getAbsolutePath();
            }
        }
    }
    
    public static String obtenerPathDeCamara()
    {
        return imagen_path;
    }
    
    public static void imagenDesdeGaleria(Activity activity)
    {
        Intent selectPictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(selectPictureIntent, "Elija una opcion"), PICTURE_FROM_GALLERY);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static String guardarImgenDeGaleria(Activity activity, Uri uri)
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File filepath = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp");
        File new_file = new File(filepath, "JPEG_" + timeStamp + "_" + System.currentTimeMillis() + ".jpg");
        
        InputStream is = null;
        OutputStream os = null;
        try
        {
            is = activity.getContentResolver().openInputStream(uri);
            os = new FileOutputStream(new_file);
            copy(is, os);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
                os.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return new_file.getAbsolutePath();
    }
    
    static void copy(@NotNull InputStream source, OutputStream target) throws IOException
    {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0)
        {
            target.write(buf, 0, length);
        }
    }
    //================================CONSULTAR FOTOS============================================
    
    public static Uri getImageUriFromPath(String photoPath)
    {
        if (photoPath.equals(""))
        {
            return null;
        }
        File f = new File(photoPath);
        return Uri.fromFile(f);
    }
    
    public static int calculateInSampleSize(int width, int heigth)
    {
        final int reqWidth = 300;
        final int reqHeight = 300;
        
        // Raw height and width of image
        int inSampleSize = 1;
        
        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((heigth / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth)
        {
            inSampleSize *= 2;
        }
        
        return inSampleSize;
    }
    
    public static Bitmap getCompressedBitmapImage(String photoPath)
    {
        
        if (photoPath.equals(""))
        {
            return null;
        }
        
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options.outWidth, options.outHeight);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return bitmap;
    }
}
