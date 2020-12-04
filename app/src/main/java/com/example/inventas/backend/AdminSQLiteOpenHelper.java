package com.example.inventas.backend;

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
    private String v_tasas = "CREATE TABLE v_tasas (id_tasa integer NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                                            "cambio_dolar real NOT NULL," +
                                            "fecha TEXT NOT NULL," +
                                            "hora TEXT NOT NULL);";

    //</editor-fold>

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {   //AQUÍ SE CREAN LAS TABLAS
        db.execSQL(v_tasas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS v_tasas");
        db.execSQL(v_tasas);
    }
}
