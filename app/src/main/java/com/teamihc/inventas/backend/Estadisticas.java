package com.teamihc.inventas.backend;

import com.teamihc.inventas.backend.entidades.Venta;

import java.util.Calendar;

/**
 * @author Karen
 */
public class Estadisticas
{
    /**
     *
     * @return retorna arreglo de strings con fecha de cada dia de la semana en curso.
     */
    private static String[] diasSemana()
    {
        String dias[] = new String[7];
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dias[0] = Herramientas.FORMATO_FECHA.format(c);

        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        dias[1] = Herramientas.FORMATO_FECHA.format(c);

        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        dias[2] = Herramientas.FORMATO_FECHA.format(c);

        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        dias[3] = Herramientas.FORMATO_FECHA.format(c);

        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        dias[4] = Herramientas.FORMATO_FECHA.format(c);

        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dias[5] = Herramientas.FORMATO_FECHA.format(c);

        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dias[6] = Herramientas.FORMATO_FECHA.format(c);

        return dias;
    }

    private static String intToDay(int index)
    {
        switch (index)
        {
            case 0:
                return "Lunes";
            case 1:
                return "Martes";
            case 2:
                return "Miercoles";
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
     * Guarda en un arreglo la ganancia obtenida cada día.
     * [0] Lunes.
     * [1] Martes.
     * [2] Miercoles.
     * [3] Jueves.
     * [4] Viernes.
     * [5] Sábado.
     * [6] Domingo.
     * @param gananciaDiaria es el arreglo donde se gruardarán los datos. NOTA: el tamaño del arreglo debe ser siete (7).
     */
    public static void calcularGananciaDiaria(float[] gananciaDiaria)
    {
        String dia[] = diasSemana();

        for (int i = 0; i < 7; i++)
            gananciaDiaria[i] = Venta.obtenerGananciaDia(dia[i]);
    }

    /**
     * Guarda en un arreglo la ganancia obtenida cada día.
     * Posiciones:
     * [0] Lunes.
     * [1] Martes.
     * [2] Miercoles.
     * [3] Jueves.
     * [4] Viernes.
     * [5] Sábado.
     * [6] Domingo.
     * @param ingresoDiario es el arreglo donde se gruardarán los datos. NOTA: el tamaño del arreglo debe ser siete (7).
     */
    public static void calcularIngresoDiario(float[] ingresoDiario)
    {
        String dia[] = diasSemana();

        for (int i = 0; i < 7; i++)
            ingresoDiario[i] = Venta.obtenerIngresoDia(dia[i]);
    }

    /**
     * Calcula el ingreso total en la semana en curso.
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
     * @return string con el nombre del día con mayor ganancia.
     */
    public static String diaMayorGanancia()
    {
        String diaSemana[] = diasSemana();
        float gananciasDiarias[] = new float[7];
        calcularGananciaDiaria(gananciasDiarias);
        float gananciaMayor = gananciasDiarias[0];
        int indexDia = -1;

        for (int i = 0; i < 7; i++)
            if (gananciasDiarias[i] > gananciaMayor)
            {
                indexDia = i;
                gananciaMayor = gananciasDiarias[i];
            }

        return intToDay(indexDia);
    }

    /**
     * @return string con el nombre del día con mayor ingreso.
     */
    public static String diaMayorIngreso()
    {
        String diaSemana[] = diasSemana();
        float ingresosDiarios[] = new float[7];
        calcularIngresoDiario(ingresosDiarios);
        float ingresoMayor = ingresosDiarios[0];
        int indexDia = -1;

        for (int i = 0; i < 7; i++)
            if (ingresosDiarios[i] > ingresoMayor)
            {
                indexDia = i;
                ingresoMayor = ingresosDiarios[i];
            }

        return intToDay(indexDia);
    }

}
