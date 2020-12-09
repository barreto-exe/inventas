package com.teamihc.inventas.backend;

import com.teamihc.inventas.backend.entidades.Venta;

import java.util.Calendar;

/**
 * @author Karen
 */
public class Estadisticas
{
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


    public static float gananciaSemanal()
    {
        float ganancia = 0;
        String dia[] = diasSemana();

        for (int i = 0; i < 7; i++)
        {
            ganancia += Venta.obtenerGananciaDia(dia[i]);
        }

        return ganancia;
    }

    public static float ingresoSemanal()
    {
        float ingreso = 0;
        String dia[] = diasSemana();

        for (int i = 0; i < 7; i++)
        {
            ingreso += Venta.obtenerIngresoDia(dia[i]);
        }

        return ingreso;
    }

    
}
