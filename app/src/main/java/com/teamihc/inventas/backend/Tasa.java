package com.teamihc.inventas.backend;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Tasa
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Precio de la tasa del día.
     */
    private float rate;
    
    
    /**
     * Fecha de registro de la nueva tasa.
     */
    private String date;
    
    
    /**
     * Hora de registro de la nueva tasa.
     */
    private String time;
    
    
    /**
     * Base de Datos.
     */
    private final SQLiteDatabase db;
    //</editor-fold>
    
    /**
     * Crea una instancia de una tasa, para poder añadir nuevas tasas a la Base de Datos.
     *
     * @param rate es el precio de la tasa.
     * @param date es la fecha de registro de la tasa.
     * @param time es la hora de registro de la tasa.
     * @param db   es la base de datos donde se guardarán las tasas.
     */
    public Tasa(float rate, String date, String time, SQLiteDatabase db)
    {
        this.rate = rate;
        this.date = date;
        this.time = time;
        this.db = db;
    }
    
    public Tasa(String date, String time, SQLiteDatabase db)
    {
        this.date = date;
        this.time = time;
        this.db = db;
    }
    
    /**
     * Método para añadir una nueva tasa a la Base de Datos.
     */
    public void addRate()
    {
        ContentValues register = new ContentValues();
        
        register.put("cambio_dolar", rate);
        register.put("fecha", date);
        register.put("hora", time);
        
        db.insert("v_tasas", null, register);
        db.close();
    }
    
    
    public float getRate()
    {
        return rate;
    }
}
