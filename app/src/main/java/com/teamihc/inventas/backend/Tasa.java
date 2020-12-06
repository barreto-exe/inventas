package com.teamihc.inventas.backend;

import android.content.ContentValues;

import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tasa
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Precio de la tasa del día.
     */
    private float monto;
    
    
    /**
     * Fecha de registro de la nueva tasa.
     */
    private Date fechaHora;
    
    //</editor-fold>
    
    /**
     * Crea una instancia de una tasa, para poder añadir nuevas tasas a la Base de Datos.
     *
     * @param monto es el precio de la tasa.
     * @param fechaHora es la fecha y hora de registro de la tasa.
     */
    public Tasa(float monto, Date fechaHora)
    {
        this.monto = monto;
        this.fechaHora = fechaHora;
    }
    
    /**
     * Método para añadir una nueva tasa a la Base de Datos.
     */
    public void addTasa()
    {
        String query = "INSERT INTO v_tasas(monto, fecha, hora) VALUES (?, ?, ?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(monto);
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fechaHora));
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(fechaHora));
        op.ejecutar();
    }
    
    
    public float getMonto()
    {
        return monto;
    }
}
