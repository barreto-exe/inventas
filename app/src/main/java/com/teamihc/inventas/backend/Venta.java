package com.teamihc.inventas.backend;

import java.util.Date;

/**
 * @author Karen
 */
public class Venta implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Tasa tasa;
    private Date fechaHora;
    //</editor-fold>
    
    public Venta(Tasa tasa, Date fechaHora)
    {
        this.tasa = tasa;
        this.fechaHora = fechaHora;
    }
    
    //<editor-fold desc="Getters & Setters">
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
    
    }
    
    @Override
    public int obtenerId()
    {
        return 0;
    }
}
