package com.teamihc.inventas.backend.basedatos;

import android.content.res.AssetManager;
import android.widget.Toast;

import com.teamihc.inventas.BuildConfig;
import com.teamihc.inventas.backend.Herramientas;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase personalizada para manipular una base de dato SQLite fácilmente. Cada instancia de DBCon se
 * encarga de realizar un comando o consulta a la Base de Datos. Si es una consulta, entonces la
 * idea es obtener de ella una matriz de objetos
 *
 * @author Luis Barreto
 * @version 0.1
 */
public class DBOperacion
{
    static
    {
        try
        {
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to register SQLDroidDriver");
        }
    }
    
    private static final String NOMBRE_BD = "inventas.sqlite";
    /**
     * La versión de la base de datos.
     * Este dato debe ser igual al que está en la tabla v_configuracion.
     */
    private static final String VERSION_BD = "12";
    /**
     * Representa la ubicación del archivo SQLite con respecto al ejecutable del programa.
     */
    private static final String PATH_BD = "jdbc:sqldroid:/data/data/" + BuildConfig.APPLICATION_ID + "/database/" + NOMBRE_BD;
    
    /**
     * Comando a ejecutar en la base de datos.
     */
    private String query;
    
    /**
     * Lista de parámetros del query.
     */
    private ArrayList<Object> parametros;
    
    /**
     * Instancia objeto con query vacío.
     */
    public DBOperacion()
    {
        this.query = "";
        parametros = new ArrayList<Object>();
    }
    
    /**
     * Instancia objeto con consulta preparada a la base de datos.
     *
     * @param query es la consulta a realizar en la base de datos.
     */
    public DBOperacion(String query)
    {
        this.query = query;
        parametros = new ArrayList<Object>();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getQuery()
    {
        return query;
    }
    
    public void setQuery(String query)
    {
        this.query = query;
    }
    //</editor-fold>
    
    /**
     * Crea una variable de conexión a la base de datos
     *
     * @return conexión a la base de datos indicada en <code>NOMBRE_BD</code>
     */
    public static Connection conexionSQLite()
    {
        Connection conn = null;
        
        try
        {
            //Intentar abrir conexión al archivo SQLite
            conn = DriverManager.getConnection(PATH_BD);
        }
        catch (SQLException e)
        {
            //Imprimir error en consola
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    /**
     * Realiza una consulta en la base de datos, y vacía los resultados en un
     * <code>DBMatriz</code>
     *
     * @return una instancia <code>DBMatriz</code> con los resultados de la consulta, o bien
     * <code>null</code> si hubo un error en la consulta.
     */
    public DBMatriz consultar()
    {
        //Instancia un statement preparado sobre la conexión global basado en el query
        try (Connection connection = conexionSQLite();
             PreparedStatement statement = connection.prepareStatement(this.query);)
        {
            //Añade los parámetros asignados dentro del statement SQL 
            for (int i = 0; i < this.parametros.size(); i++)
            {
                //Indica el index y el valor del parámetro en ese índice.
                statement.setObject(i + 1, parametros.get(i));
            }
            
            //Obteniendo resultados y metadata de la consulta
            ResultSet set = statement.executeQuery();
            ResultSetMetaData metaData = set.getMetaData();
            int cantColumnas = metaData.getColumnCount();
            
            //Leyendo nombre de las columndas y colocándolas en array
            ArrayList<String> columnas = new ArrayList<String>();
            for (int i = 0; i < cantColumnas; i++)
            {
                columnas.add(metaData.getColumnLabel(i + 1));
            }
            
            //Instanciando matriz con las columnas correspondientes
            DBMatriz matriz = new DBMatriz(columnas);
            
            //Leer todas las filas de la consulta
            while (set.next())
            {
                //Instanciar array para guardar datos de fila
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++)
                {
                    fila[i] = set.getObject(i + 1);
                }
                
                //Agregar datos leídos
                matriz.agregarFila(fila);
            }
            
            return matriz;
        }
        catch (SQLException e)
        {
            //Imprimir error en consola
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Ejecuta el query asignado pasado por parámetro, el cual debe ser de tipo
     * <code>INSERT</code>, <code>UPDATE</code> o <code>DELETE</code>; o un
     * comando SQL que no retorna nada.
     *
     * @return la cantidad de registros afectados por el comando.
     */
    public int ejecutar()
    {
        try (Connection connection = conexionSQLite();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            for (int i = 0; i < this.parametros.size(); i++)
            {
                //Indica el index y el valor del parámetro en ese índice.
                statement.setObject(i + 1, parametros.get(i));
            }
            
            return statement.executeUpdate();
        }
        catch (SQLException e)
        {
            //Imprimir error en consola
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    /**
     * Añade el valor de un parámetro representado con un <code>?</code> dentro del query.
     *
     * @param valor representa el valor del parámetro.
     */
    public void pasarParametro(Object valor)
    {
        parametros.add(valor);
    }
    
    /**
     * Verifica la versión actual de la base de datos, si está desactualizada, hace un upgrade.
     * @param assetManager asset manager del activity.
     */
    public static void verificarBaseDatos(AssetManager assetManager)
    {
        boolean existe = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/database/" + NOMBRE_BD).exists();
        
        //Si la base de datos existe, verificar si se debe actualizar
        if(existe)
        {
            String query = "SELECT version_bbdd FROM v_configuracion";
            DBOperacion op = new DBOperacion(query);
            DBMatriz resultado = op.consultar();

            resultado.leer();
            String version = (String) resultado.getValor("version_bbdd");

            //Si está actualizada, no hago nada
            if(version.equals(VERSION_BD))
            {
                return;
            }
        }
        
        //Actualizar la base de datos
        Herramientas.copyFileOrDir("database", assetManager);
    }
    
}
