package com.teamihc.inventas.backend.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Clase para crear la base de datos de InVentas
 * @author Karen
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper
{
    //<editor-fold defaultstate="collapsed" desc="Declaración de Tablas en SQL">
    /**
     * String para crear tabla de Tasas.
     */
    private String v_tasas = "CREATE TABLE v_tasas (id_tasa integer NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                                                    "cambio_dolar real NOT NULL," +
                                                    "fecha TEXT NOT NULL," +
                                                    "hora TEXT NOT NULL);";

    /**
     * String para crear tabla de Ventas que relaciona el ID de una venta con el ID de la tasa
     * en el momento de la venta, el monto total a pagar (en dólares),
     * la fecha y hora en el que se procesó la venta.
     */
    private String v_ventas = "CREATE TABLE v_ventas (id_venta integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                                                    "id_tasa integer NOT NULL," +
                                                    "total real NOT NULL," +
                                                    "fecha TEXT NOT NULL," +
                                                    "hora TEXT NOT NULL);";

    /**
     * String para crear tabla que relaciona el ID de una venta con los artículos
     * pertenecientes a dicha venta, la cantidad correspondiente a cada artículo
     * y el Subtotal en dólares.
     */
    private String v_detalles_ventas = "CREATE TABLE v_detalles_ventas (id_venta integer NOT NULL," +
                                                                        "id_articulo integer NOT NULL," +
                                                                        "cantidad integer NOT NULL," +
                                                                        "subtotal real NOT NULL);";

    /**
     * String para crear tabla que relaciona el ID de una venta con el ID de la moneda
     * que fue utilizada para el pago de dicha venta, la descripción del tipo de pago,
     * y el monto total que fue cancelado bajo esa modalidad.
     */
    private String v_pagos = "CREATE TABLE v_pagos (id_venta integer NOT NULL," +
                                                    "id_moneda integer NOT NULL," +
                                                    "tipo_de_pago TEXT NOT NULL," +
                                                    "monto real NOT NULL);";

    /**
     * String para crear tabla Monedas, donde guardan los IDs correspondientes
     * a las monedas aceptadas (Bolívar y Dólar)
     */
    private String v_monedas = "CREATE TABLE v_monedas (id_moneda integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                                                        "nombre TEXT(7)," +
                                                        "simbolo TEXT(5));";

    //</editor-fold>

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {   //AQUÍ SE CREAN LAS TABLAS
        db.execSQL(v_tasas);
        db.execSQL(v_ventas);
        db.execSQL(v_detalles_ventas);
        db.execSQL(v_pagos);
        db.execSQL(v_monedas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        /*HAY QUE VER QUÉ HACER AQUÍ PARA QUE NO SE PIERDAN LOS DATOS EN UNA ACTUALIZACIÓN DE LA APP*/
    }
}
