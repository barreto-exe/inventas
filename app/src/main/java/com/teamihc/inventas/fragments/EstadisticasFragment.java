package com.teamihc.inventas.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Estadisticas;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.backend.entidades.Venta;

import java.util.ArrayList;
import java.util.Collections;

public class EstadisticasFragment extends Fragment {
    private View view;
    private TextView descripcionMasVendido, precioBsMasVendido, cantidadMasVendido, diaMasVentas, ventas_diaMasVentas;
    private TextView diaMasIngresos, ingresos_diaMasIngresos, descripcionMenosVendido, precioBsMenosVendido, cantidadMenosVendido, diaMenosVentas;
    private TextView ventas_diaMenosVentas, diaMenosIngresos, ingresos_diaMenosIngresos, ingresoTotal, gananciaTotal;
    private ImageView imagenMasVendido, imagenMenosVendido;
    ArrayList<BarEntry> cambioVenta;
    int[] listaVenta= new int[7];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        init();
        BarChart barChart = view.findViewById(R.id.estadisticasChart);
        llenarChart();
        BarDataSet barDataSet = new BarDataSet(cambioVenta, "Resumen de ventas de la semana");
        barDataSet.setColor(getResources().getColor(R.color.bars));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(13f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);


        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refrescarEstadisticas();
    }

    public void llenarChart() {
       /* cambioVenta = new ArrayList<>();
        Estadisticas.calcularVentasDiaria(listaVenta);
        for (int i = 0; i < 7; i++) {
             BarEntry b=new BarEntry(i,listaVenta[i]);
            cambioVenta.add(b);
        }
*/
        cambioVenta = new ArrayList<>();
        cambioVenta.add(new BarEntry(20,192));
        cambioVenta.add(new BarEntry(30,532));
        cambioVenta.add(new BarEntry(60,130));
        cambioVenta.add(new BarEntry(70,323));
        cambioVenta.add(new BarEntry(88,224));
        cambioVenta.add(new BarEntry(90,192));
    }


    private void refrescarEstadisticas() {

        float gananciaT = Estadisticas.gananciaTotalSemanal();
        float ingresoT = Estadisticas.ingresoTotalSemanal();
        Articulo masV = Estadisticas.articuloMasVendido();
        Articulo menosV = Estadisticas.articuloMenosVendido();
        String diaMasV=Estadisticas.diaMayorCantVentas();
        String diaMasI=Estadisticas.diaMayorIngreso();
        String diaMenosV=Estadisticas.diaMenorCantVentas();
        String diaMenosI= Estadisticas.diaMenorIngreso();




        if (gananciaT > 0) {
            gananciaTotal.setText("" + gananciaT);
        } else {
            gananciaTotal.setText("-");
        }

        if (ingresoT > 0) {
            ingresoTotal.setText("" + ingresoT);
        } else {
            ingresoTotal.setText("-");
        }

       //if (masV!=null) {

        //} else {
            descripcionMasVendido.setText("-");
            precioBsMasVendido.setText("-");
            cantidadMasVendido.setText("-");
            imagenMasVendido.setImageResource(R.color.colorPrimary);
        //}

        //if (menosV!=null) {

        //} else {
            descripcionMenosVendido.setText("-");
            precioBsMenosVendido.setText("-");
            cantidadMenosVendido.setText("-");
            imagenMenosVendido.setImageResource(R.color.colorPrimary);
        //}

        if(diaMasV!=null){
            diaMasVentas.setText(diaMasV);
            ventas_diaMasVentas.setText(""+Estadisticas.mayorCantVentas());
        }else{
            diaMasVentas.setText("-");
            ventas_diaMasVentas.setText("-");
        }


        if(diaMasI!=null){
            diaMasIngresos.setText(diaMasI);
            ingresos_diaMasIngresos.setText(""+Estadisticas.mayorIngreso());
        }else{
            diaMasIngresos.setText("-");
            ingresos_diaMasIngresos.setText("-");
        }

        if(diaMenosV!=null){
            diaMenosVentas.setText(diaMenosV);
            ventas_diaMenosVentas.setText(""+Estadisticas.menorCantVentas());
        }else{
            diaMenosVentas.setText("-");
            ventas_diaMenosVentas.setText("-");
        }


        if(diaMenosI!=null){
            diaMenosIngresos.setText(diaMenosI);
            ingresos_diaMenosIngresos.setText(""+Estadisticas.menorIngreso());
        }else{
            diaMenosIngresos.setText("-");
            ingresos_diaMenosIngresos.setText("-");
        }


    }


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


    }


}
