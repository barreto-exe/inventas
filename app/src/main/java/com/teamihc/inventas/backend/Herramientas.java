package com.teamihc.inventas.backend;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.teamihc.inventas.BuildConfig;
import com.teamihc.inventas.activities.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Clase con variables y métodos misceláneos
 */
public class Herramientas
{
    public static final String FORMATO_FECHA_STRING = "dd/MM/yyyy";
    public static final String FORMATO_TIEMPO_STRING = "hh:mm:ss";
    public static final String SIMBOLO_BS = "Bs.S";
    public static final String SIMBOLO_D  = "$";
    
    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING);
    public static final SimpleDateFormat FORMATO_TIEMPO = new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING);
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
}
