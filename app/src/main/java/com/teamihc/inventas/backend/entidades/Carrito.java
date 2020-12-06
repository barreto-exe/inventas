package com.teamihc.inventas.backend.entidades;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author Karen
 */
public class Carrito
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private ArrayList<ArticuloPxQ> carrito;
    //</editor-fold>

    /**
     * Crea una instancia de un carrito vacío, que luego podrá ser manipulado.
     */
    public Carrito(){ carrito = new ArrayList<ArticuloPxQ>(); }

    public ArrayList<ArticuloPxQ> getCarrito() { return carrito; }

    /**
     * Método para agregar un artículo al carrito y especificar la cantidad de unidades
     * vendidas.
     * @param articulo es el artículo que se desea agregar al carrito.
     * @param cantidad es el número de unidades que se venderán de dicho artículo.
     * @return retorna 0 si no se cuenta con una cantidad suficiente en el almacén para cubrir la demanda.
     * retorna 1 si se pudo agregar el artículo al carrito con éxito.
     */
    public int agregarArticulo(@NonNull Articulo articulo, int cantidad)
    {
        if (articulo.getCantidad() < cantidad)
            return 0;

        carrito.add(new ArticuloPxQ(articulo,cantidad));

        return 1;
    }

    /**
     * Método para eliminar un artículo del carrito.
     * @param articulo es el artículo que se desea eliminar del carrito.
     */
    public void eliminarArticulo(@NonNull Articulo articulo)
    {
        for (ArticuloPxQ a: carrito)
        {
            if (a.getArticulo().obtenerId() == articulo.obtenerId())
            {
                carrito.remove(a);
                return;
            }
        }
    }

    /**
     * Método para calcular el monto total a pagar por los artículos que se encuentran en el carrito.
     * @return retorna el monto total a pagar (retorna 0 si el carrito está vacío).
     */
    public float obtenerTotal()
    {
        float total = 0;

        if (!carrito.isEmpty())
        {
            for (ArticuloPxQ a : carrito)
            {
                total += a.getSubTotal();
            }
        }

        return total;
    }
}
