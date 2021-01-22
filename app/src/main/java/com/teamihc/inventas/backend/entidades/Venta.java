package com.teamihc.inventas.backend.entidades;

import com.teamihc.inventas.backend.Estadisticas;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.teamihc.inventas.backend.basedatos.DBMatriz;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Karen
 */
public class Venta implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Tasa tasa;
    private Date fechaHora;
    private Carrito carrito;
    //</editor-fold>

    /**
     * Crea una instancia de una Venta, que posteriormente podrá ser registrada
     * en la Base de Datos con sus respectivos detalles.
     *
     * @param tasa es la tasa en el momento en el que se instancia una venta.
     * @param fechaHora es la fecha y la hora en el momento en el que se instancia una venta.
     */
    public Venta(Tasa tasa, Date fechaHora)
    {
        this.tasa = tasa;
        this.fechaHora = fechaHora;
        carrito = new Carrito();
    }

    public Venta(Tasa tasa, Date fechaHora, Carrito carrito)
    {
        this.tasa = tasa;
        this.fechaHora = fechaHora;
        this.carrito = carrito;
    }

    //<editor-fold desc="Getters & Setters">
    public Tasa getTasa(){ return tasa; }
    public void setTasa(Tasa tasa){ this.tasa = tasa; }
    public Date getFechaHora(){ return fechaHora; }
    public void setFechaHora(Date fechaHora){ this.fechaHora = fechaHora; }
    private void setCarrito(ArrayList<ArticuloPxQ> lista){ this.carrito.setCarrito(lista); }
    public void setCarrito(Carrito carrito){ this.carrito = carrito; }
    public Carrito getCarrito() { return carrito; }
    //</editor-fold>

    @Override
    public boolean registrar()
    {
        /* Si el carrito está vacío, no procesa la venta */
        if (carrito.getCarrito().isEmpty())
            return false;

        /*Se registran los datos correspondientes en la tabla de v_ventas y se genera el id_venta*/
        String query = "INSERT INTO v_ventas(id_tasa, total, fecha, hora, ganancia) VALUES (?, ?, ?, ?, ?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(tasa.obtenerId());
        op.pasarParametro(carrito.obtenerTotalDolares());
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(fechaHora));
        op.pasarParametro(Herramientas.FORMATO_TIEMPO.format(fechaHora));
        op.pasarParametro(carrito.obtenerGanancia());
        op.ejecutar();

        for (ArticuloPxQ a : carrito.getCarrito())
        {
            /* Se registran los detalles de venta de cada artículo vendido en la tabla v_detalles_ventas */
            query = "INSERT INTO v_detalles_ventas(id_venta, id_articulo, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            op = new DBOperacion(query);
            op.pasarParametro(obtenerId());
            op.pasarParametro(a.getArticulo().obtenerId());
            op.pasarParametro(a.getCantidad());
            op.pasarParametro(a.getSubTotal());
            op.ejecutar();

            /* Se registra la salida del articulo en v_inventario y se actualiza la cantidad disponible */
            a.getArticulo().agregarStock(a.getCantidad() * -1, fechaHora);
        }
        return true;
    }

    @Override
    public int obtenerId()
    {
        String query =
                "SELECT id_venta FROM v_ventas WHERE " +
                        "fecha = ? " +
                        "AND hora = ? " +
                        "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(fechaHora));
        op.pasarParametro(Herramientas.FORMATO_TIEMPO.format(fechaHora));

        DBMatriz resultado = op.consultar();

        int id = -1;
        if(resultado.leer())
        {
            id = (int) resultado.getValor("id_venta");
        }
        return id;
    }

    public static void cargarVentasDiaEnLista(ArrayList<Venta> lista, Date fecha)
    {
        String query = "SELECT * FROM v_ventas WHERE fecha = ? ORDER BY id_venta DESC";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fecha));
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
        {
            int id = (int) resultado.getValor("id_venta");

            lista.add(obtenerInstancia(id));
        }
    }

    public static void cargarVentasTotalesEnLista(ArrayList<Venta> lista)
    {
        String query = "SELECT * FROM v_ventas ORDER BY id_venta DESC";
        DBOperacion op = new DBOperacion(query);
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
        {
            int id = (int) resultado.getValor("id_venta");

            lista.add(obtenerInstancia(id));
        }
    }

    public static void cargarVentasRangoEnLista(ArrayList<Venta> lista, Date desde, Date hasta)
    {
        String query = "SELECT * FROM v_ventas WHERE fecha >= ? AND fecha <= ? ORDER BY id_venta DESC";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(desde));
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(hasta));
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
        {
            int id = (int) resultado.getValor("id_venta");

            lista.add(obtenerInstancia(id));
        }
    }

    /**
     * Método para calcular la cantidad de referencias que se encuentran en el carrito.
     * @return retorna la cantidad de referencias en el carrito.
     */
    public int cantidadReferencias()
    {
        return carrito.getCarrito().size();
    }

    public static Venta obtenerInstancia(int id)
    {
        String query = "SELECT * FROM v_ventas WHERE id_venta = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(id);
        DBMatriz resultado = op.consultar();

        if (resultado.leer())
        {
            String fechaVenta = (String) resultado.getValor("fecha");
            String horaVenta  = (String) resultado.getValor("hora");

            Venta venta = null;

            try
            {
                venta = new Venta(
                        Tasa.obtenerTasa((int) resultado.getValor("id_tasa")),
                        Herramientas.FORMATO_FECHATIEMPO.parse(fechaVenta + " " + horaVenta)
                );

                ArrayList<ArticuloPxQ> factura = new ArrayList<ArticuloPxQ>();
                Carrito.cargarFacturaEnLista(factura, id);
                venta.setCarrito(factura);

                return venta;
            }
            catch (ParseException e)
            {
            }
        }

        return null;
    }

    /**
     * Método para obtener la cantidad de ventas obtenidas en un día determinado.
     * @param fecha es la fecha del día que se quiere obtener la ganancia total.
     */
    public static int obtenerVentasDia(String fecha)
    {
        int cantidadVentas = 0;
        String query = "SELECT * FROM v_ventas WHERE fecha = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(fecha);
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
            cantidadVentas++;

        return cantidadVentas;
    }
    public static int cantidadVentasRegistradas()
    {
        String query = "SELECT COUNT(*) AS cantidad FROM v_ventas";
        DBOperacion op = new DBOperacion(query);
        DBMatriz resultado = op.consultar();
        if(resultado.leer() && resultado.getValor("cantidad") != null)
        {
            return (int) resultado.getValor("cantidad");
        }
        return 0;
    }

    /**
     * Método para calcular el monto total (en dólares) a pagar por los artículos que se encuentran en el carrito.
     * @return retorna el monto total a pagar (retorna 0 si el carrito está vacío).
     */
    public float obtenerTotalDolares()
    {
        return carrito.obtenerTotalDolares();
    }

    /**
     * Método para calcular el monto total (en bolívares) a pagar por los artículos que se encuentran en el carrito.
     * @return retorna el monto total a pagar (retorna 0 si el carrito está vacío).
     */
    public float obtenerTotalBsS()
    {
        return carrito.obtenerTotalBsS(tasa);
    }
}
