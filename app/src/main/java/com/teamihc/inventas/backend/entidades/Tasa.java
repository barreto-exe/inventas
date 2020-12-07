package com.teamihc.inventas.backend.entidades;

import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tasa implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private float monto;
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
    
    //<editor-fold desc="Getters & Setters">
    public float getMonto()
    {
        return monto;
    }
    public void setMonto(float monto)
    {
        this.monto = monto;
    }
    public Date getFechaHora()
    {
        return fechaHora;
    }
    public void setFechaHora(Date fechaHora)
    {
        this.fechaHora = fechaHora;
    }
    //</editor-fold>
    
    /**
     * Método para añadir una nueva tasa a la Base de Datos.
     */
    @Override
    public void registrar()
    {
        String query = "INSERT INTO v_tasas(monto, fecha, hora) VALUES (?, ?, ?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(monto);
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fechaHora));
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(fechaHora));
        op.ejecutar();
    }
    
    @Override
    public int obtenerId()
    {
        String query =
                "SELECT id_tasa FROM v_tasas WHERE " +
                "fecha = ? " +
                "AND hora = ? " +
                "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fechaHora));
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(fechaHora));
    
        DBMatriz resultado = op.consultar();
    
        int id = -1;
        if(resultado.leer())
        {
            id = (int) resultado.getValor("id_tasa");
        }
        return id;
    }
}
