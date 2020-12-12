package com.teamihc.inventas.backend.entidades;

import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tasa implements Entidad
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private float monto;
    private Date fechaHora;
    //</editor-fold>

    /**
     * Crea una instancia de una tasa, para poder añadir nuevas tasas a la Base de Datos.
     *
     * @param monto     es el precio de la tasa.
     * @param fechaHora es la fecha y hora de registro de la tasa.
     */
    public Tasa(float monto, Date fechaHora)
    {
        this.monto = monto;
        this.fechaHora = fechaHora;
    }

    /**
     * Crea una instancia de una tasa, para poder añadir nuevas tasas a la Base de Datos.
     *
     * @param monto es el precio de la tasa.
     * @param fecha en formato dd/MM/yyyy
     * @param hora  en formato hh:mm:ss
     */
    public Tasa(float monto, String fecha, String hora)
    {
        try
        {
            this.fechaHora = Herramientas.FORMATO_FECHATIEMPO.parse(fecha + " " + hora);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.monto = monto;
    }

    //<editor-fold desc="Getters & Setters">
    public float getMonto()
    {
        return monto;
    }

    public void setMonto(float monto)
    {
        this.monto = monto;
    }

    public Date getFechaHora()
    {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora)
    {
        this.fechaHora = fechaHora;
    }
    //</editor-fold>

    /**
     * Método para añadir una nueva tasa a la Base de Datos.
     */
    @Override
    public boolean registrar()
    {
        String query = "INSERT INTO v_tasas(monto, fecha, hora) VALUES (?, ?, ?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(monto);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(fechaHora));
        op.pasarParametro(Herramientas.FORMATO_TIEMPO.format(fechaHora));
        op.ejecutar();

        return true;
    }

    @Override
    public int obtenerId()
    {
        String query =
                "SELECT id_tasa FROM v_tasas WHERE " +
                        "fecha = ? " +
                        "AND hora = ? " +
                        "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(fechaHora));
        op.pasarParametro(Herramientas.FORMATO_TIEMPO.format(fechaHora));

        DBMatriz resultado = op.consultar();

        int id = -1;
        if (resultado.leer())
        {
            id = (int) resultado.getValor("id_tasa");
        }
        return id;
    }

    public static void cargarHistoricoEnLista(ArrayList<Tasa> lista)
    {
        String query = "SELECT * FROM v_tasas ORDER BY id_tasa DESC";
        DBOperacion op = new DBOperacion(query);
        DBMatriz resultado = op.consultar();

        while (resultado.leer())
        {
            Tasa tasa = new Tasa(
                    (Float) resultado.getValor("monto"),
                    (String) resultado.getValor("fecha"),
                    (String) resultado.getValor("hora")
            );

            if(tasa.monto > 1)
            {
                lista.add(tasa);
            }
        }
    }
    
    public static void cargarHistoricoEnLista(ArrayList<Tasa> lista, int limite)
    {
        String query = "SELECT * FROM v_tasas ORDER BY id_tasa DESC LIMIT ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(limite);
        DBMatriz resultado = op.consultar();
        
        while (resultado.leer())
        {
            Tasa tasa = new Tasa(
                    (Float) resultado.getValor("monto"),
                    (String) resultado.getValor("fecha"),
                    (String) resultado.getValor("hora")
            );
            
            if(tasa.monto > 1)
            {
                lista.add(tasa);
            }
        }
    }
    
    /**
     * @return la tasa del día.
     */
    public static Tasa obtenerTasa()
    {
        String query = "SELECT * FROM v_tasas ORDER BY id_tasa DESC LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        DBMatriz resultado = op.consultar();

        if(resultado.leer())
        {
            return new Tasa(
                    (Float) resultado.getValor("monto"),
                    (String) resultado.getValor("fecha"),
                    (String) resultado.getValor("hora")
            );
        }

        return null;
    }

    /**
     * @param id es el id bajo el cuál está registrada la tasa que se busca.
     * @return la tasa correspondiente al id.
     */
    public static Tasa obtenerTasa(int id)
    {
        String query = "SELECT * FROM v_tasas WHERE id_tasa = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(id);
        DBMatriz resultado = op.consultar();

        if(resultado.leer())
            return new Tasa(
                    (Float) resultado.getValor("monto"),
                    (String) resultado.getValor("fecha"),
                    (String) resultado.getValor("hora")
            );

        return null;
    }

    /**
     * Consulta la tasa registrada anterior a la actual.
     * @return la tasa anterior, o null si no existe.
     */
    public Tasa getTasaAnterior()
    {
        String query = "SELECT * FROM v_tasas WHERE id_tasa = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(obtenerId() - 1);
        DBMatriz resultado = op.consultar();

        if (resultado.leer())
        {
            return new Tasa(
                    (Float) resultado.getValor("monto"),
                    (String) resultado.getValor("fecha"),
                    (String) resultado.getValor("hora")
            );
        }

        return null;
    }

    /**
     * Compara el porcentaje de cambio con respecto a la tasa registrada anterior.
     * @return
     */
    public float getPorcentajeCambio()
    {
        try
        {
            Tasa tasaAnterior = getTasaAnterior();
            return (this.monto - tasaAnterior.monto) / tasaAnterior.monto * 100;
        }
        catch (Exception ex)
        {
            return 0;
        }
    }

    @Override
    public String toString()
    {
        return "Tasa{" +
                "monto=" + monto +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
