package com.teamihc.inventas.backend;


public class Articulo implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String descripcion;
    private float costo;
    private float precio;
    private String codigo;
    //</editor-fold>
    
    
    /**
     * Crea una instancia de un artículo, para poder hacer las operaciones básicas en la Base de
     * Datos (CRUD). Nota: Para un nuevo producto, la cantidad se settea automaticamente en 0
     *
     * @param descripcion es la descripción del artículo (nombre, marca, peso neto, etc).
     * @param costo        es el costo del arículo en dólares.
     * @param precio       es el precio de venta del arículo en dólares.
     * @param codigo        es el código de barras del artículo.
     *                    artículo.
     */
    public Articulo(String descripcion, float costo, float precio, String codigo)
    {
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.codigo = codigo;
    }
    
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