package com.teamihc.inventas.fragments;

import android.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Tasa;

import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class TasasFragment extends Fragment
{
    private View view;
    private TextView tasaDia;
    private TextView tasaAnterior;
    private ImageView iconoSubida;
    ArrayList<BarEntry> cambioTasa;
    ArrayList<Tasa> listaTasas;
    
    //aqui se vacia la info de la tasa actual y de la estadistica del cambio de tasas
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tasas, container, false);
        refrescarTasaDia();
        
        BarChart barChart = view.findViewById(R.id.tasaChart);
        llenarChart();
        BarDataSet barDataSet = new BarDataSet(cambioTasa, "Cambio de la tasa del dólar");
        barDataSet.setColor(getResources().getColor(R.color.bars));
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(7f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
        
        
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        refrescarTasaDia();
    }
    
    public void llenarChart()
    {
        listaTasas = new ArrayList<>();
        Tasa.cargarHistoricoEnLista(listaTasas, 7);
        cambioTasa = new ArrayList<>();
        Collections.reverse(listaTasas);
        for (int i = 0; i < listaTasas.size(); i++)
        {
            BarEntry b = new BarEntry(i, listaTasas.get(i).getMonto());
            cambioTasa.add(b);
        }
        
    }
    
    /**
     * Muestra la tasa del día y la anterior en la vista.
     */
    private void refrescarTasaDia()
    {
        tasaDia = (TextView) view.findViewById(R.id.tasaDia);
        tasaAnterior = (TextView) view.findViewById(R.id.tasaAnterior);
        iconoSubida = (ImageView) view.findViewById(R.id.iconoSubida);
        Tasa tasa = Tasa.obtenerTasa();
        Tasa anterior = tasa.getTasaAnterior();
        if (tasa.getMonto() > 1)
        {
            iconoSubida.setVisibility(View.VISIBLE);
            
            tasaDia.setText(Herramientas.formatearMonedaBs(tasa.getMonto()));
            tasaAnterior.setText(Herramientas.formatearMonedaBs(anterior.getMonto()));
            
            float porcentaje = tasa.getPorcentajeCambio();
            if (porcentaje > 0)
            {
                iconoSubida.setImageResource(R.drawable.ic_arrow_drop_up_24px);
            }
            else if (porcentaje < 0)
            {
                iconoSubida.setImageResource(R.drawable.ic_arrow_drop_down_24px);
            }
        }
        else
        {
            iconoSubida.setVisibility(View.GONE);
            tasaDia.setText("¡Ingresa una tasa!");
            tasaAnterior.setText("-");
        }
    }
}

