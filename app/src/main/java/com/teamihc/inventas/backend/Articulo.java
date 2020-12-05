package com.teamihc.inventas.backend;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Articulo
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Descripción del artículo (nombre, marca, peso neto).
     */
    private String description;

    /**
     * Costo del artículo (en dólares).
     */
    private float cost;

    /**
     * Precio de venta (en dólares).
     */
    private float price;

    /**
     * Código de barras.
     */
    private String code;

    /**
     * Base de Datos.
     */
    private final SQLiteDatabase db;

    //</editor-fold>


    /**
     * Crea una instancia de un artículo, para poder hacer las operaciones básicas
     * en la Base de Datos (CRUD).
     * Nota: Para un nuevo producto, la cantidad se settea automaticamente en 0
     *
     * @param description es la descripción del artículo (nombre, marca, peso neto, etc).
     * @param cost es el costo del arículo en dólares.
     * @param price es el precio de venta del arículo en dólares.
     * @param code es el código de barras del artículo.
     * @param db es la base de datos donde se realizanrán las operaciones con dicho artículo.
     */
    public Articulo(String description, float cost, float price, String code, SQLiteDatabase db)
    {
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.code = code;
        this.db = db;
    }


    /**
     * Método para añadir un artículo nuevo al inventario.
     * @return -1 si falta algún dato del artículo.
     * 0 si ya existe un artículo con caracteristicas iguales.
     * 1 si se añadió correctamente al inventario.
     */
    public int addToInventory()
    {
        if (description == null || cost == 0 || price == 0 || code == null)
            return -1;

        Cursor row = db.rawQuery("SELECT id_articulo FROM v_articulos WHERE codigo = " + code, null);

        if (row.moveToFirst())
        {
            return 0;
        }
        else
        {
            ContentValues register = new ContentValues();

            register.put("descripcion", description);
            register.put("costo_unitario", cost);
            register.put("precio_venta", price);
            register.put("cantidad", 0);
            register.put("codigo", code);

            db.insert("v_articulos", null, register);
            db.close();
            return 1;
        }

    }

}
        /*AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper(context, "InVentas", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();*/