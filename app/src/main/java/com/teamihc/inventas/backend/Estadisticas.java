package com.teamihc.inventas.backend;

import android.util.Log;

import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Venta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Karen
 */
public class Estadisticas
{
    /**
     * @return retorna arreglo de Dates con la fecha del primer y último día de la semana en curso.
     * Desde [0] DOMINGO (primer día de la semana). Hasta [2] SÁBADO (último día de la semana).
     */
    public static Date[] limiteSemana()
    {
        Date dias[] = new Date[2];
        Calendar c = Calendar.getInstance();
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dias[0] = c.getTime();
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        c.add(Calendar.DATE, 1);
        dias[1] = c.getTime();
        
        return dias;
    }
    
    /**
     * @return retorna arreglo de strings con fecha de cada dia de la semana en curso.
     */
    public static String[] diasSemana()
    {
        String dias[] = new String[7];
        Calendar c = Calendar.getInstance();
        
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dias[0] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        dias[1] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        dias[2] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        dias[3] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        dias[4] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dias[5] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        c.add(Calendar.DATE, 1);
        dias[6] = Herramientas.FORMATO_FECHA.format(c.getTime());
        
        return dias;
    }
    
    public static String intToDay(int index)
    {
        switch (index)
        {
            case 0:
                return "Lunes";
            case 1:
                return "Martes";
            case 2:
                return "Miércoles";
            case 3:
                return "Jueves";
            case 4:
                return "Viernes";
            case 5:
                return "Sábado";
            case 6:
                return "Domingo";
        }
        return null;
    }
    
    /**
     * Calcula la ganancia total en la semana en curso.
     *
     * @return monto de la ganancia obtenida en la semana.
     */
    public static float gananciaTotalSemanal()
    {
        float ganancia = 0;
        String dia[] = diasSemana();
        
        for (int i = 0; i < 7; i++)
        {
            ganancia += Venta.obtenerGananciaDia(dia[i]);
        }
        
        return ganancia;
    }
    
    /**
     * Guarda en un arreglo la ganancia obtenida cada día. [0] Domingo. [1] Lunes. [2] Martes. [3]
     * Miercoles. [4] Jueves. [5] Viernes. [6] Sábado.
     *
     * @param gananciaDiaria es el arreglo donde se gruardarán los datos. NOTA: el tamaño del
     *                       arreglo debe ser siete (7).
     */
    public static void calcularGananciaDiaria(float[] gananciaDiaria)
    {
        String dia[] = diasSemana();
        
        for (int i = 0; i < 7; i++)
            gananciaDiaria[i] = Venta.obtenerGananciaDia(dia[i]);
    }
    
    /**
     * Guarda en un arreglo la ganancia obtenida cada día. Posiciones:[0] Domingo. [1] Lunes. [2]
     * Martes. [3] Miercoles. [4] Jueves. [5] Viernes. [6] Sábado.
     *
     * @param ingresoDiario es el arreglo donde se gruardarán los datos. NOTA: el tamaño del arreglo
     *                      debe ser siete (7).
     */
    public static void calcularIngresoDiario(float[] ingresoDiario)
    {
        String dia[] = diasSemana();
        
        for (int i = 0; i < 7; i++)
            ingresoDiario[i] = Venta.obtenerIngresoDia(dia[i]);
    }
    
    /**
     * Guarda en un arreglo la cantidad de ventas obtenida cada día. Posiciones: [0] Domingo. [1]
     * Lunes. [2] Martes. [3] Miércoles. [4] Jueves. [5] Viernes. [6] Sábado.
     *
     * @param ventasDiaria es el arreglo donde se gruardarán los datos. NOTA: el tamaño del arreglo
     *                     debe ser siete (7).
     */
    public static void calcularVentasDiaria(int[] ventasDiaria)
    {
        String dia[] = diasSemana();
        
        for (int i = 0; i < 7; i++)
            ventasDiaria[i] = Venta.obtenerVentasDia(dia[i]);
    }
    
    /**
     * Calcula el ingreso total en la semana en curso.
     *
     * @return monto del ingreso obtenid en la semana.
     */
    public static float ingresoTotalSemanal()
    {
        float ingreso = 0;
        String dia[] = diasSemana();
        
        for (int i = 0; i < 7; i++)
        {
            ingreso += Venta.obtenerIngresoDia(dia[i]);
        }
        
        return ingreso;
    }
    
    /**
     * @return string con el nombre del día con mayor ingreso.
     */
    public static String diaMayorIngreso()
    {
        float ingresosDiarios[] = new float[7];
        calcularIngresoDiario(ingresosDiarios);
    
        int indexDia = 0;
        float ingresoMayor = ingresosDiarios[indexDia];
        
        for (int i = 0; i < 7; i++)
            if (ingresosDiarios[i] > ingresoMayor)
            {
                ingresoMayor = ingresosDiarios[i];
                indexDia = i;
            }
        
        return intToDay(indexDia);
    }
    /**
     * @return string con el nombre del día con menor ingreso.
     */
    public static String diaMenorIngreso()
    {
        float ingresosDiarios[] = new float[7];
        calcularIngresoDiario(ingresosDiarios);
    
        int indexDia = 0;
        float ingresoMenor = ingresosDiarios[indexDia];
        
        for (int i = 0; i < 7; i++)
            if (ingresosDiarios[i] < ingresoMenor)
            {
                ingresoMenor = ingresosDiarios[i];
                indexDia = i;
            }
        
        return intToDay(indexDia);
    }
    
    /**
     * @return string con el nombre del día con mayor cantidad de ventas.
     */
    public static String diaMayorCantVentas()
    {
        int ventasDiarias[] = new int[7];
        calcularVentasDiaria(ventasDiarias);
    
        int indexDia = 0;
        float diaMayor = ventasDiarias[indexDia];
        
        for (int i = 0; i < 7; i++)
            if (ventasDiarias[i] > diaMayor)
            {
                diaMayor = ventasDiarias[i];
                indexDia = i;
            }
        
        return intToDay(indexDia);
    }
    /**
     * @return string con el nombre del día con menor cantidad de ventas.
     */
    public static String diaMenorCantVentas()
    {
        int ventasDiarias[] = new int[7];
        calcularVentasDiaria(ventasDiarias);
    
        int indexDia = 0;
        int diaMenor = ventasDiarias[indexDia];
        
        for (int i = 0; i < 7; i++)
            if (ventasDiarias[i] < diaMenor)
            {
                diaMenor = ventasDiarias[i];
                indexDia = i;
            }
        
        return intToDay(indexDia);
    }
    
    /**
     * @return mayor cantidad de ventas de la semana.
     */
    public static int mayorCantVentas()
    {
        int ventasDiarias[] = new int[7];
        calcularVentasDiaria(ventasDiarias);

        int diaMayor = ventasDiarias[0];
        
        for (int i = 0; i < 7; i++)
            if (ventasDiarias[i] > diaMayor)
            {
                diaMayor = ventasDiarias[i];
            }
        
        return diaMayor;
    }
    /**
     * @return menor cantidad de ventas de la semana.
     */
    public static int menorCantVentas()
    {
        int ventasDiarias[] = new int[7];
        calcularVentasDiaria(ventasDiarias);
        
        int diaMenor = ventasDiarias[0];
        
        for (int i = 0; i < 7; i++)
            if (ventasDiarias[i] < diaMenor)
            {
                diaMenor = ventasDiarias[i];
            }
        
        return diaMenor;
    }
    
    /**
     * @return el mayor ingreso de la semana.
     */
    public static float mayorIngreso()
    {
        float ingresosDiarios[] = new float[7];
        calcularIngresoDiario(ingresosDiarios);
        
        float diaMayor = ingresosDiarios[0];
        
        for (int i = 0; i < 7; i++)
            if (ingresosDiarios[i] > diaMayor)
            {
                diaMayor = ingresosDiarios[i];
            }
        
        return diaMayor;
    }
    /**
     * @return el menor ingreso de la semana.
     */
    public static float menorIngreso()
    {
        float ingresosDiarios[] = new float[7];
        calcularIngresoDiario(ingresosDiarios);
        
        float diaMenor = ingresosDiarios[0];
        
        for (int i = 0; i < 7; i++)
            if (ingresosDiarios[i] < diaMenor)
            {
                diaMenor = ingresosDiarios[i];
            }
        
        return diaMenor;
    }
    
    /**
     * Calcula el artículo más vendido en un rango de tiempo.
     *
     * @param desde la fecha de inicio del rango (inclusivo).
     * @param hasta la fecha de fin del rango (inclusivo)
     * @return Arreglo de objetos en este orden: [0] Instancia del artículo más vendido, [1] Integer
     * - unidades vendidas, [2] Float - total ganancia producida por sus ventas.
     */
    public static Object[] articuloMasVendido(Date desde, Date hasta)
    {
        String query =
                "SELECT d.id_articulo, SUM( d.cantidad ) AS unidades_vendidas, SUM( v.ganancia ) AS total_ganancia " +
                        "FROM v_detalles_ventas d  " +
                        "INNER JOIN v_ventas v ON (v.id_venta = d.id_venta)  " +
                        "WHERE fecha >= ? AND fecha <= ?  " +
                        "GROUP BY d.id_articulo  " +
                        "ORDER BY unidades_vendidas DESC " +
                        "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(desde));
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(hasta));
        DBMatriz resultado = op.consultar();
        
        if (resultado.leer())
        {
            ArrayList<Object> resultadoQuery = new ArrayList<>();
            resultadoQuery.add(Articulo.obtenerInstancia((int) resultado.getValor("id_articulo")));
            resultadoQuery.add(new Integer((int) resultado.getValor("unidades_vendidas")));
            resultadoQuery.add(new Float((float) resultado.getValor("total_ganancia")));
            return resultadoQuery.toArray();
        }
        
        return null;
    }
    
    /**
     * Calcula el artículo menos vendido en un rango de tiempo.
     *
     * @param desde la fecha de inicio del rango (inclusivo).
     * @param hasta la fecha de fin del rango (inclusivo)
     * @return Arreglo de objetos en este orden: [0] Instancia del artículo más vendido, [1] Integer
     * - unidades vendidas, [2] Float - total ganancia producida por sus ventas.
     */
    public static Object[] articuloMenosVendido(Date desde, Date hasta)
    {
        String query =
                "SELECT d.id_articulo, SUM( d.cantidad ) AS unidades_vendidas, SUM( v.ganancia ) AS total_ganancia " +
                        "FROM v_detalles_ventas d  " +
                        "INNER JOIN v_ventas v ON (v.id_venta = d.id_venta)  " +
                        "WHERE fecha >= ? AND fecha <= ?  " +
                        "GROUP BY d.id_articulo  " +
                        "ORDER BY unidades_vendidas ASC " +
                        "LIMIT 1";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(desde));
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(hasta));
        DBMatriz resultado = op.consultar();
        
        if (resultado.leer())
        {
            ArrayList<Object> resultadoQuery = new ArrayList<>();
            resultadoQuery.add(Articulo.obtenerInstancia((int) resultado.getValor("id_articulo")));
            resultadoQuery.add(new Integer((int) resultado.getValor("unidades_vendidas")));
            resultadoQuery.add(new Float((float) resultado.getValor("total_ganancia")));
            return resultadoQuery.toArray();
        }
        
        return null;
    }
    
    /**
     * Calcula las ganancias obtenidas en un día.
     *
     * @param dia que se quiere consultar.
     * @return ganancias de ese día.
     */
    public static float gananciasPorDia(Date dia)
    {
        String query = "SELECT SUM(ganancia) AS ganancia FROM v_ventas WHERE fecha = ?";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(Herramientas.FORMATO_FECHA.format(dia));
        
        DBMatriz resultados = op.consultar();
        if (resultados.leer())
        {
            try
            {
                float result = (float) resultados.getValor("ganancia");
                return result;
            }
            catch (Exception exception)
            {
            
            }
        }
        return 0;
    }
}
