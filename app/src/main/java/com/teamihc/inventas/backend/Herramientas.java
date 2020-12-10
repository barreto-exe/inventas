package com.teamihc.inventas.backend;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Clase con variables y métodos misceláneos
 */
public class Herramientas
{
    public static final String FORMATO_FECHA_STRING = "yyyy/MM/dd";
    public static final String FORMATO_FECHA_FRONT_STRING = "dd/MM/yyyy";
    public static final String FORMATO_TIEMPO_STRING = "HH:mm:ss";
    public static final String FORMATO_TIEMPO_FRONT_STRING = "hh:mm a";
    public static final String SIMBOLO_BS = "Bs.S";
    public static final String SIMBOLO_D  = "$";
    
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
        FORMATO_FECHATIEMPO = new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING + " " +Herramientas.FORMATO_TIEMPO_STRING);
        
        simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        
        FOMATO_MONEDA = new DecimalFormat("###,###,###,##0.00", simbolos);
        FOMATO_PORCENTAJE = new DecimalFormat("#0.00%", simbolos);
    }

    public static String formatearDiaFecha(Date fecha)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);
        int index = calendar.get(Calendar.DAY_OF_WEEK);

        String dia = Estadisticas.intToDay(index -1);

        return dia + ", " + Herramientas.FORMATO_FECHA_FRONT.format(Calendar.getInstance().getTime());
    }
    
    public static String formatearMonedaBs(float monto)
    {
        if(monto == 0)
            return SIMBOLO_BS + " 0";
        return SIMBOLO_BS + " " + FOMATO_MONEDA.format(monto);
    }
    
    public static String formatearMonedaDolar(float monto)
    {
        if(monto == 0)
            return SIMBOLO_D + " 0";
        return SIMBOLO_D + " " + FOMATO_MONEDA.format(monto);
    }
    
    public static String formatearPorcentaje(float porcentaje)
    {
        if(porcentaje == 0)
            return "0%";
        return Herramientas.FOMATO_PORCENTAJE.format(porcentaje);
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

    public static byte[] bitmapToArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap blobToBitmap(SQLDroidBlob blob){
        byte[] array = null;

        try {
            int size = (int) blob.length();
            array = blob.getBytes(0, size);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static Bitmap comprimirImagen(Bitmap imagen){
        byte[] array = bitmapToArray(imagen);

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(array, 0, array.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options.outHeight, options.outWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static int calculateInSampleSize(int height, int width) {
        // Raw height and width of image
        final int reqHeight = 4000;
        final  int reqWidth = 4000;
        int inSampleSize = 1;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((height / inSampleSize) >= reqHeight && (width / inSampleSize) >= reqWidth) {
            inSampleSize *= 2;
        }

        return inSampleSize;
    }
}
