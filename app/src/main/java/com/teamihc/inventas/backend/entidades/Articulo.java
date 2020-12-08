package com.teamihc.inventas.backend.entidades;


import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.DoubleBinaryOperator;

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
        this.descripcion = descripcion.trim();
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
        return Math.round(precio*100.0f)/100.0f;
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
    public float getPrecioBs()
    {
        Tasa tasaDia = Tasa.obtenerTasa();
        
        float precioBs = 0;
        if(tasaDia != null)
        {
            precioBs = precio * tasaDia.getMonto();
        }
        else
        {
            precioBs = precio;
        }
        return Math.round(precioBs*100.0f)/100.0f;
    }
    //</editor-fold>
    
    @Override
    public void registrar()
    {
        String query =
                "INSERT INTO v_articulos (descripcion, costo_unitario, precio_venta, cantidad, codigo) " +
                        "VALUES (?,?,?,?,?);";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(descripcion);
        op.pasarParametro(costo);
        op.pasarParametro(precio);
        op.pasarParametro(cantidad);
        op.pasarParametro(codigo);
        
        op.ejecutar();
        
        agregarStock(cantidad, Calendar.getInstance().getTime());
    }
    
    @Override
    public int obtenerId()
    {
        String query =
                "SELECT id_articulo FROM v_articulos WHERE " +
                        "descripcion = ? " +
                        "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(descripcion);
        
        DBMatriz resultado = op.consultar();
        
        int id = -1;
        if (resultado.leer())
        {
            id = (int) resultado.getValor("id_articulo");
        }
        return id;
    }
    
    /**
     * Obtiene una instancia del artículo que corresponda a la descripción indicada.
     *
     * @param descripcion del artículo.
     * @return instancia del artículo de tipo Articulo
     */
    public static Articulo obtenerInstancia(@NotNull String descripcion)
    {
        String query = "SELECT * FROM v_articulos WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(descripcion.trim());
        
        DBMatriz resultado = op.consultar();
        if (resultado.leer())
        {
            float costo = (float) resultado.getValor("costo_unitario");
            float precio = (float) resultado.getValor("precio_venta");
            int cantidad = (int) resultado.getValor("cantidad");
            String codigo = (String) resultado.getValor("codigo");
            
            return new Articulo(descripcion, costo, precio, cantidad, codigo);
        }
        
        return null;
    }
    
    public static void cargarInventarioEnLista(ArrayList<Articulo> listaArticulos)
    {
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
    
    /**
     * Ingresa un nuevo movimiento en v_inventario y actualiza el total del stock (cantidad) en
     * v_artículos.
     *
     * @param cantidad  sumada o restada del stock.
     * @param fechaHora en la que se realizó el movimiento.
     */
    public void agregarStock(int cantidad, Date fechaHora)
    {
        /* Registrar stock en v_inventario */
        String query = "INSERT INTO v_inventario(fecha, hora, id_articulo, cantidad) VALUES (?, ?, ?, ?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(fechaHora));
        op.pasarParametro(Herramientas.FORMATO_TIEMPO.format(fechaHora));
        op.pasarParametro(obtenerId());
        op.pasarParametro(cantidad);
        op.ejecutar();
    }
    
    /**
     * Consulta el stock del artículo.
     *
     * @return la cantidad de unidades, o -1 si el artículo no está registrado en la BBDD
     */
    public int cantidadStock()
    {
        int id = obtenerId();
        if (id == -1)
        {
            return -1;
        }
        
        String query = "SELECT cantidad FROM v_articulos WHERE id = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(id);
        DBMatriz resultado = op.consultar();
        resultado.leer();
        
        return (int) resultado.getValor("cantidad");
    }
    
    public void actualizar()
    {
        String query = "UPDATE v_articulos SET costo_unitario = ?, precio_venta = ?, cantidad = ?, codigo = ? WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(costo);
        op.pasarParametro(precio);
        op.pasarParametro(cantidad);
        op.pasarParametro(codigo);
        op.pasarParametro(descripcion);
        op.ejecutar();
    }
    
    public void eliminar()
    {
        String query = "DELETE FROM v_articulos WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(descripcion);
        op.ejecutar();
    }
}