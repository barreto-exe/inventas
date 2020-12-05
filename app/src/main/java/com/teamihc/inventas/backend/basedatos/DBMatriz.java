package com.teamihc.inventas.backend.basedatos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Un objeto con el que se pueden operar los resultados de las consultas a las bases de datos hechas
 * con <code>DBOperacion</code>.
 * <p>
 * La lógica tras su implementación hace uso de una cola para los resultados.
 *
 * <code>DBMatriz</code> complementa a <code>DBOperacion</code> y se acopla a ella.
 * <p>
 * En la versión actual, se puede instanciar esta clase desde cualquier lugar, pero no es el deber
 * ser. En el futuro se implementará de tal forma que solo se puede obtener una instancia de la
 * clase mediante el método <code>consultar</code> de <code>DBOperacion</code>.
 *
 * @author Luis Barreto
 * @version 0.1
 */
public class DBMatriz
{
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Lista de los nombres de las columnas que arroja la consulta.
     */
    private ArrayList<String> columnas;
    
    /**
     * Una cola con las filas y los valores en cada una.
     */
    private Queue<Object[]> filas;
    
    /**
     * Indica si puede ser leída o no;
     */
    private boolean abierta;
    //</editor-fold>
    
    /**
     * Un objeto con el que se pueden operar los resultados de las consultas a las bases de datos
     * hechas con <code>DBOperacion</code>.
     *
     * @param columnas lista previamente leída de los nombres de las columnas que arroja la
     *                 consulta.
     */
    public DBMatriz(ArrayList<String> columnas)
    {
        this.abierta = false;
        this.columnas = columnas;
        
        //Inicializar cola de filas de consulta
        filas = new LinkedList<>();
        
        //Añadir fila vacía para permitir funcionamiento de cola
        filas.add(new Object[0]);
    }
    
    
    public String[] getColumnas()
    {
        return (String[]) columnas.toArray();
    }
    
    
    /**
     * Obtener variable contenida en la fila leída actual.
     *
     * @param columna es el valor de la fila a leer.
     * @return el valor de la fila correspondiente a la columna indicada. Si la columna no existe,
     * retorna <code>null</code>
     */
    public Object getValor(String columna)
    {
        //Si no está abierta, es porque ya se leyeron todos los posibles resultados.
        if (!abierta)
            return null;
        
        //Identificar índice de la columna indicada
        int indexColumna = columnas.indexOf(columna);
        
        //Retornar null si no existe la columna
        if (indexColumna == -1)
        {
            return null;
        }
        
        //Retornar el valor de la fila correspondiente a la columna.
        return filas.peek()[indexColumna];
    }
    
    /**
     * Método utilizado en <code>DBOperacion</code>. Agrega a la matriz una fila leída en la
     * consulta.
     *
     * @param fila es la fila agregada.
     */
    public void agregarFila(Object[] fila)
    {
        filas.add(fila);
    }
    
    /**
     * Remueve la fila leída actualmente en la matriz y pasa a la siguiente.
     *
     * @return <code>true</code> si existe fila siguiente, <code>false</code> si no existe.
     */
    public boolean leer()
    {
        abierta = true;
        
        //Desechar fila leída actual
        filas.poll();
        
        //Verificar si la siguiente es nula
        if (filas.peek() == null)
        {
            abierta = false;
            return false;
        }
        
        //Si no es nula, retornar verdadero para permitir lectura
        return true;
    }
}