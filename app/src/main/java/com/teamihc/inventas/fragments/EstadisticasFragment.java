package com.teamihc.inventas.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Estadisticas;
import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;
import java.util.Date;

public class EstadisticasFragment extends Fragment {
    private View view;
    private TextView descripcionMasVendido, precioBsMasVendido, cantidadMasVendido, diaMasVentas, ventas_diaMasVentas;
    private TextView diaMasIngresos, ingresos_diaMasIngresos, descripcionMenosVendido, precioBsMenosVendido, cantidadMenosVendido, diaMenosVentas;
    private TextView ventas_diaMenosVentas, diaMenosIngresos, ingresos_diaMenosIngresos, ingresoTotal, gananciaTotal;
    private ImageView imagenMasVendido, imagenMenosVendido;

    private Spinner desicion;
    ArrayList<BarEntry> cambioVenta;
    int[] listaVenta = new int[7];
    float[] listaIngresos = new float[7];
    String[] opciones = {"Número de ventas", "Ingreso en dólares", "Ganancia en dólares"};
    BarChart barChart;
    BarDataSet barDataSet;
    String diaMasV;
    String diaMasI;
    String diaMenosV;
    String diaMenosI;
    float ingresoT, gananciaT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        init();
        barChart = view.findViewById(R.id.estadisticasChart);
        spinnerLlenado();
        seleccionSpinner();
        //Aqui se establece todo lo de la grafica
        barDataSet = new BarDataSet(cambioVenta, "Resumen de la semana");
        barDataSet.setColor(getResources().getColor(R.color.bars));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(13f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
        //Fin grafica

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refrescarEstadisticas();
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }


    /**
     * LLenado de gráficos
     */
    public void llenarChartVentas() {
        cambioVenta = new ArrayList<>();
        Estadisticas.calcularVentasDiaria(listaVenta);
        for (int i = 0; i < 7; i++) {
            BarEntry b = new BarEntry(i, listaVenta[i]);
            cambioVenta.add(b);
        }
    }

    public void llenarChartIngresos() {
        cambioVenta = new ArrayList<>();
        Estadisticas.calcularIngresoDiario(listaIngresos);
        for (int i = 0; i < 7; i++) {
            BarEntry b = new BarEntry(i, listaIngresos[i]);
            cambioVenta.add(b);
        }


    }

    public void llenarChartGanancias() {
        cambioVenta = new ArrayList<>();
        Estadisticas.calcularGananciaDiaria(listaIngresos);
        for (int i = 0; i < 7; i++) {
            BarEntry b = new BarEntry(i, listaIngresos[i]);
            cambioVenta.add(b);
        }
    }


    /**
     * Tratado del spinner
     */
    public void spinnerLlenado() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                opciones);
        desicion.setAdapter(adapter);
    }


    /**
     * LLena el gráfico a mostrar
     */
    public void seleccionSpinner() {
        String seleccion = desicion.getSelectedItem().toString();

        if (seleccion.equals(opciones[0])) {
            llenarChartVentas();

            barChart.notifyDataSetChanged();
            barChart.invalidate();
        }
        if (seleccion.equals(opciones[1])) {
            llenarChartIngresos();
            barChart.notifyDataSetChanged();
            barChart.invalidate();

        }
        if (seleccion.equals(opciones[2])) {
            llenarChartGanancias();
            barChart.notifyDataSetChanged();
            barChart.invalidate();

        }

    }


    /**
     * Se actualzan los datos dentro del fragment
     */

    private void refrescarEstadisticas() {

        Date[] semana = new Date[2];
        semana = Estadisticas.limiteSemana();
        gananciaT = Estadisticas.gananciaTotalSemanal();
        ingresoT = Estadisticas.ingresoTotalSemanal();
        Object[] objMas;
        objMas = Estadisticas.articuloMasVendido(semana[0], semana[1]);

        Object[] objMenos;
        objMenos = Estadisticas.articuloMenosVendido(semana[0], semana[1]);
        Articulo masV, menosV;

        diaMasV = Estadisticas.diaMayorCantVentas();
        diaMasI = Estadisticas.diaMayorIngreso();
        diaMenosV = Estadisticas.diaMenorCantVentas();
        diaMenosI = Estadisticas.diaMenorIngreso();

        if (Articulo.cantidadArticulosRegistrados() > 0) {
            masV = (Articulo) objMas[0];
            menosV = (Articulo) objMenos[0];
            if (masV != null) {
                descripcionMasVendido.setText(masV.getDescripcion());
                precioBsMasVendido.setText("$" + Float.toString((float) objMas[2]));
                cantidadMasVendido.setText(Integer.toString((int) objMas[1]));
                imagenMasVendido.setImageBitmap(masV.getImagen());
            }

            if (menosV != null && !(menosV.getDescripcion().equals(masV.getDescripcion()))) {
                descripcionMenosVendido.setText(menosV.getDescripcion());
                precioBsMenosVendido.setText("$" + Float.toString((float) objMenos[2]));
                cantidadMenosVendido.setText(Integer.toString((int) objMenos[1]));
                imagenMenosVendido.setImageBitmap(menosV.getImagen());
            } else {
                descripcionMenosVendido.setText("-");
                precioBsMenosVendido.setText("-");
                cantidadMenosVendido.setText("-");
                imagenMenosVendido.setImageResource(R.color.colorPrimary);
            }

        } else {
            descripcionMasVendido.setText("-");
            precioBsMasVendido.setText("-");
            cantidadMasVendido.setText("-");
            imagenMasVendido.setImageResource(R.color.colorPrimary);
            descripcionMenosVendido.setText("-");
            precioBsMenosVendido.setText("-");
            cantidadMenosVendido.setText("-");
            imagenMenosVendido.setImageResource(R.color.colorPrimary);
        }


        if (gananciaT > 0) {
            gananciaTotal.setText("$" + "" + gananciaT);
        } else {
            gananciaTotal.setText("-");
        }

        if (ingresoT > 0) {
            ingresoTotal.setText("$" + "" + ingresoT);
        } else {
            ingresoTotal.setText("-");
        }


        if (diaMasV != null && Estadisticas.mayorCantVentas() > 0) {
            diaMasVentas.setText(diaMasV);
            ventas_diaMasVentas.setText("" + Estadisticas.mayorCantVentas());
        } else {
            diaMasVentas.setText("-");
            ventas_diaMasVentas.setText("-");
        }


        if (diaMasI != null && Estadisticas.mayorIngreso() > 0) {
            diaMasIngresos.setText(diaMasI);
            ingresos_diaMasIngresos.setText("$" + "" + Estadisticas.mayorIngreso());
        } else {
            diaMasIngresos.setText("-");
            ingresos_diaMasIngresos.setText("-");
        }

        if (diaMenosV != null && Estadisticas.menorCantVentas() > 0) {
            diaMenosVentas.setText(diaMenosV);
            ventas_diaMenosVentas.setText("" + Estadisticas.menorCantVentas());
        } else {
            diaMenosVentas.setText("-");
            ventas_diaMenosVentas.setText("-");
        }


        if (diaMenosI != null && Estadisticas.menorIngreso() > 0) {
            diaMenosIngresos.setText(diaMenosI);
            ingresos_diaMenosIngresos.setText("$" + "" + Estadisticas.menorIngreso());
        } else {
            diaMenosIngresos.setText("-");
            ingresos_diaMenosIngresos.setText("-");
        }


    }

    /**
     * Inicializando todos los elementos de la parte grafica
     */

    public void init() {
        descripcionMasVendido = (TextView) view.findViewById(R.id.descripcionMasVendido);
        precioBsMasVendido = (TextView) view.findViewById(R.id.precioBsMasVendido);
        cantidadMasVendido = (TextView) view.findViewById(R.id.cantidadMasVendido);
        imagenMasVendido = (ImageView) view.findViewById(R.id.imagenMasVendido);


        diaMasVentas = (TextView) view.findViewById(R.id.diaMasVentas);
        ventas_diaMasVentas = (TextView) view.findViewById(R.id.ventas_diaMasVentas);

        diaMasIngresos = (TextView) view.findViewById(R.id.diaMasIngresos);
        ingresos_diaMasIngresos = (TextView) view.findViewById(R.id.ingresos_diaMasIngresos);

        descripcionMenosVendido = (TextView) view.findViewById(R.id.descripcionMenosVendido);
        precioBsMenosVendido = (TextView) view.findViewById(R.id.precioBsMenosVendido);
        cantidadMenosVendido = (TextView) view.findViewById(R.id.cantidadMenosVendido);
        imagenMenosVendido = (ImageView) view.findViewById(R.id.imagenMenosVendido);

        diaMenosVentas = (TextView) view.findViewById(R.id.diaMenosVentas);
        ventas_diaMenosVentas = (TextView) view.findViewById(R.id.ventas_diaMenosVentas);

        diaMenosIngresos = (TextView) view.findViewById(R.id.diaMenosIngresos);
        ingresos_diaMenosIngresos = (TextView) view.findViewById(R.id.ingresos_diaMenosIngresos);

        ingresoTotal = (TextView) view.findViewById(R.id.ingresoTotal);
        gananciaTotal = (TextView) view.findViewById(R.id.gananciaTotal);

        desicion = (Spinner) view.findViewById(R.id.decision);

    }


}
