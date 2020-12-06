package com.teamihc.inventas.backend.entidades;


import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.util.ArrayList;

public class Articulo implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String descripcion;
    private float costo;
    private float precio;
    private int cantidad;
    private String codigo;
    //</editor-fold>
    
    
    /**
     * Crea una instancia de un artículo, para poder hacer las operaciones básicas en la Base de
     * Datos (CRUD). Nota: Para un nuevo producto, la cantidad se settea automaticamente en 0
     *
     * @param descripcion es la descripción del artículo (nombre, marca, peso neto, etc).
     * @param costo       es el costo del arículo en dólares.
     * @param precio      es el precio de venta del arículo en dólares.
     * @param cantidad    stock del artículo en el inventario.
     * @param codigo      es el código de barras del artículo.
     */
    public Articulo(String descripcion, float costo, float precio, int cantidad, String codigo)
    {
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.codigo = codigo;
    }
    
    //<editor-fold desc="Getters & Setters">
    public String getDescripcion()
    {
        return descripcion;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    public float getCosto()
    {
        return costo;
    }
    public void setCosto(float costo)
    {
        this.costo = costo;
    }
    public float getPrecio()
    {
        return precio;
    }
    public void setPrecio(float precio)
    {
        this.precio = precio;
    }
    public int getCantidad()
    {
        return cantidad;
    }
    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }
    public String getCodigo()
    {
        return codigo;
    }
    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
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

    public static void cargarInventarioEnLista(ArrayList<Articulo> listaArticulos){
        String query = "SELECT * FROM v_articulos ORDER BY descripcion ASC";
        DBOperacion op = new DBOperacion(query);
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
        {
            Articulo articulo = new Articulo(
                    (String) resultado.getValor("descripcion"),
                    (Float) resultado.getValor("costo_unitario"),
                    (Float) resultado.getValor("precio_venta"),
                    (Integer) resultado.getValor("cantidad"),
                    (String) resultado.getValor("codigo"));
            listaArticulos.add(articulo);
        }
    }
}