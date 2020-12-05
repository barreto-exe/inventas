package com.teamihc.inventas.backend;

/**
 * @author Karen
 */
public class Venta
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * ID correspondiente a la venta.
     */
    private int idVenta;


    /**
     * ID tasa del día.
     */
    private int idRate;


    /**
     * Monto total a pagar.
     */
    private float total;


    /**
     * Fecha de la venta.
     */
    private String date;


    /**
     * Hora de la venta.
     */
    private String time;

    //</editor-fold>


    public Venta(int idRate, String date, String time)
    {
        this.idRate = idRate;
        this.date = date;
        this.time = time;
    }
}
