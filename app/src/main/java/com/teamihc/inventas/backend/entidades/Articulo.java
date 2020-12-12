package com.teamihc.inventas.backend.entidades;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import org.jetbrains.annotations.NotNull;
import org.sqldroid.SQLDroidBlob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.DoubleBinaryOperator;

import static com.teamihc.inventas.backend.Herramientas.*;

public class Articulo implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String descripcion;
    private float costo;
    private float precio;
    private int cantidad;
    private String codigo;
    String imagen_path;
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

    public Articulo(String descripcion, float costo, float precio, int cantidad, String codigo, String imagen_path)
    {
        this.descripcion = descripcion.trim();
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.imagen_path = imagen_path;
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
    public String getImagen_path() {
        return imagen_path;
    }
    public void setImagen_path(String imagen_path) {
        this.imagen_path = imagen_path;
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
        return precioBs;
    }
    
    public boolean isActivo()
    {
        String query =
                "SELECT estado FROM v_articulos WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(descripcion);
        DBMatriz resultado = op.consultar();
        if (resultado.leer() && ((String)resultado.getValor("estado")).equals("activo"))
        {
            return true;
        }
        return false;
    }
    //</editor-fold>

    @Override
    public boolean registrar()
    {
        String query =
                "INSERT INTO v_articulos (descripcion, costo_unitario, precio_venta, cantidad, codigo, imagen, estado) " +
                        "VALUES (?,?,?,?,?,?,?);";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(descripcion);
        op.pasarParametro(costo);
        op.pasarParametro(precio);
        op.pasarParametro(cantidad);
        op.pasarParametro(codigo);
        op.pasarParametro(imagen_path);
        op.pasarParametro("activo");

        if (op.ejecutar() != 0){
            agregarStock(cantidad, Calendar.getInstance().getTime());
            return true;
        }
        
       return false;
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
            String imagen_path = (String) resultado.getValor("imagen");
            
            return new Articulo(descripcion, costo, precio, cantidad, codigo, imagen_path);
        }
        
        return null;
    }

    /**
     * Obtiene una instancia del artículo que corresponda al ID indicado.
     *
     * @param id del artículo.
     * @return instancia del artículo de tipo Articulo
     */
    public static Articulo obtenerInstancia(int id)
    {
        String query = "SELECT * FROM v_articulos WHERE id_articulo = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(id);
        DBMatriz resultado = op.consultar();

        if (resultado.leer())
        {
            String descripcion = (String) resultado.getValor("descripcion");
            float costo = (float) resultado.getValor("costo_unitario");
            float precio = (float) resultado.getValor("precio_venta");
            int cantidad = (int) resultado.getValor("cantidad");
            String codigo = (String) resultado.getValor("codigo");
            String imagen_path = (String) resultado.getValor("imagen");

            return new Articulo(descripcion, costo, precio, cantidad, codigo, imagen_path);
        }

        return null;
    }
    
    public static void cargarInventarioEnLista(ArrayList<Articulo> listaArticulos)
    {
        String query = "SELECT * FROM v_articulos WHERE estado = ? ORDER BY descripcion ASC";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro("activo");
        DBMatriz resultado = op.consultar();
        
        while (resultado.leer())
        {
            Articulo articulo = new Articulo(
                    (String) resultado.getValor("descripcion"),
                    (Float) resultado.getValor("costo_unitario"),
                    (Float) resultado.getValor("precio_venta"),
                    (Integer) resultado.getValor("cantidad"),
                    (String) resultado.getValor("codigo"),
                    (String) resultado.getValor("imagen") );
            listaArticulos.add(articulo);
        }
    }
    
    public static int cantidadArticulosRegistrados()
    {
        String query = "SELECT COUNT(*) AS cantidad FROM v_articulos WHERE estado = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro("activo");
        DBMatriz resultado = op.consultar();
        if(resultado.leer() && resultado.getValor("cantidad") != null)
        {
            return (int) resultado.getValor("cantidad");
        }
        return 0;
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
        String query =
                "INSERT INTO v_inventario(fecha, hora, id_articulo, cantidad) VALUES (?, ?, ?, ?);";
        int id = obtenerId();
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(fechaHora));
        op.pasarParametro(Herramientas.FORMATO_TIEMPO.format(fechaHora));
        op.pasarParametro(id);
        op.pasarParametro(cantidad);
        op.ejecutar();
    
        query = "UPDATE v_articulos SET cantidad = (SELECT SUM(cantidad) FROM v_inventario WHERE id_articulo = ?) WHERE id_articulo = ?;";
        op = new DBOperacion(query);
        op.pasarParametro(id);
        op.pasarParametro(id);
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
        
        String query = "SELECT cantidad FROM v_articulos WHERE id_articulo = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(id);
        DBMatriz resultado = op.consultar();
        
        if (resultado.leer())
        {
            return (int) resultado.getValor("cantidad");
        }
        return -1;
    }
    
    public void actualizar()
    {
        //Obetener vieja foto
        Articulo articulo_viejo = Articulo.obtenerInstancia(descripcion);
        String imagen_path_viejo = articulo_viejo.getImagen_path();

        //Si la foto se modifico
        if (!imagen_path.equals(imagen_path_viejo)){
            File foto_vieja = new File(imagen_path_viejo);
            foto_vieja.delete();
        }

        String query = "UPDATE v_articulos SET costo_unitario = ?, precio_venta = ?, cantidad = ?, " +
                "codigo = ?, imagen = ? WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(costo);
        op.pasarParametro(precio);
        op.pasarParametro(cantidad);
        op.pasarParametro(codigo);
        op.pasarParametro(imagen_path);
        op.pasarParametro(descripcion);

        op.ejecutar();
    }
    
    public void eliminar()
    {
        reiniciarStock();
        
        String query = "UPDATE v_articulos SET estado = ? WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro("inactivo");
        op.pasarParametro(descripcion);
        op.ejecutar();
    }

    public void reiniciarStock()
    {
        int stock = cantidadStock();
    
        if(stock > 0)
        {
            agregarStock(-stock,Calendar.getInstance().getTime());
        }
    }
    
    public void activar()
    {
        String query = "UPDATE v_articulos SET estado = ? WHERE descripcion = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro("activo");
        op.pasarParametro(descripcion);
        op.ejecutar();
    }
    
    public static int calcularCantVendidosDia(int id, int fecha)
    {
        int cantidad = 0;

        String query = "SELECT * FROM v_detalles_ventas WHERE id_articulo = ? AND fecha = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(id);
        op.pasarParametro(fecha);
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
        {
            cantidad += (int) resultado.getValor("cantidad");
        }

        return cantidad;
    }
}