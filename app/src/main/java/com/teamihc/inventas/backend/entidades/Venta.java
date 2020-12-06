package com.teamihc.inventas.backend.entidades;

import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.text.SimpleDateFormat;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
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
        this.total = total;
        this.tasa = tasa;
        this.fechaHora = fechaHora;
        carrito = new Carrito();
    }
    
    //<editor-fold desc="Getters & Setters">
    public Tasa getTasa(){ return tasa; }
    public void setTasa(Tasa tasa){ this.tasa = tasa; }
    public Date getFechaHora(){ return fechaHora; }
    public void setFechaHora(Date fechaHora){ this.fechaHora = fechaHora; }
    //</editor-fold>
    
    @Override
    public void registrar()
    {
        /* Si el carrito está vacío, no procesa la venta */
        if (carrito.getCarrito().isEmpty())
            return;

        /*Se registran los datos correspondientes en la tabla de v_ventas y se genera el id_venta*/
        String query = "INSERT INTO v_ventas(id_tasa, total, fecha, hora) VALUES (?, ?, ?, ?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(tasa.obtenerId());
        op.pasarParametro(carrito.obtenerTotal());
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_FECHA_STRING).format(fechaHora));
        op.pasarParametro(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(fechaHora));
        op.ejecutar();

        /* PENDIENTE: extraer el id_venta de la base de datos */

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

            /* PENDIENTE: preguntar si las ventas se reflejan en el inventario de forma negativa */

            /* PENDIENTE: modificar la cantidad de un artículo en v_articulos */
        }

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
