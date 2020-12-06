package com.teamihc.inventas.backend.entidades;

import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Karen
 */
public class Venta implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Tasa tasa;
    private Date fechaHora;
    private float total;
    //</editor-fold>
    
    public Venta(float total, Tasa tasa, Date fechaHora)
    {
        this.total = total;
        this.tasa = tasa;
        this.fechaHora = fechaHora;
    }
    
    //<editor-fold desc="Getters & Setters">
    public float getTotal()
    {
        return total;
    }
    public void setTotal(float total)
    {
        this.total = total;
    }
    public Tasa getTasa()
    {
        return tasa;
    }
    public void setTasa(Tasa tasa)
    {
        this.tasa = tasa;
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
    
    @Override
    public void registrar()
    {
        String query =
                "INSERT INTO v_ventas (id_tasa,total,fecha,hora) " +
                "VALUES (?,?,?,?);";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(tasa.obtenerId());
        op.pasarParametro(total);
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fechaHora));
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(fechaHora));
    
        op.ejecutar();
    }
    
    @Override
    public int obtenerId()
    {
        String query =
                "SELECT id FROM v_ventas WHERE" +
                "fecha = ? " +
                "AND hora = ?" +
                "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fechaHora));
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(fechaHora));
    
        DBMatriz resultado = op.consultar();
    
        int id = -1;
        if(resultado.leer())
        {
            id = (int) resultado.getValor("id_venta");
        }
        return id;
    }
}
