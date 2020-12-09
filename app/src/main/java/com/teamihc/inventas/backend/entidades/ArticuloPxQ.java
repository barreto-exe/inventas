package com.teamihc.inventas.backend.entidades;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Clase que relaciona un artículo con la cantidad de unidades vendidas y el subtotal a pagar
 * por las mismas. Las instancias de esta clase conforman el carrito de una venta.
 * @author Karen
 */
public class ArticuloPxQ
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Articulo articulo;
    private int cantidad;
    private float subTotal;
    private float ganancias;
    //</editor-fold>

    /**
     * @param articulo es el artículo que se desea vender.
     * @param cantidad es la cantidad de unidades que se desea vender de dicho artículo.
     */
    public ArticuloPxQ(@NotNull Articulo articulo, int cantidad)
    {
        this.articulo = articulo;
        this.cantidad = cantidad;
        subTotal = articulo.getPrecio() * cantidad;
        ganancias = (articulo.getPrecio() - articulo.getCosto()) * cantidad;
    }

    //<editor-fold desc="Getters & Setters">
    public Articulo getArticulo() { return articulo; }
    public void setArticulo(@NotNull Articulo articulo) { this.articulo = articulo; subTotal = articulo.getPrecio() * cantidad;}
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; subTotal = articulo.getPrecio() * cantidad;}
    public float getSubTotal() { return subTotal; }
    public float getGanancias() { return ganancias; }
    //</editor-fold>


    @NotNull
    @Override
    public String toString() {
        return cantidad + " x " + articulo.getDescripcion();
    }
}
