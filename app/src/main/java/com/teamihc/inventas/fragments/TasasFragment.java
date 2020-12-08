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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Tasa;

import java.util.ArrayList;


public class TasasFragment extends Fragment
{
    private View view;
    private TextView tasaDia;
    private TextView tasaAnterior;
    private ImageView iconoSubida;
    ArrayList<BarEntry> cambioTasa;
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
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData=new BarData(barDataSet);
        barChart.setFitBars(true);
        barData.setBarWidth(2f);

        barChart.setData(barData);
        barChart.getDescription().setText("Tasa");
        barChart.animateY(2000);



        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        refrescarTasaDia();
    }

    public void llenarChart(){
        cambioTasa = new ArrayList<>();
        cambioTasa.add(new BarEntry(20,192));
        cambioTasa.add(new BarEntry(30,532));
        cambioTasa.add(new BarEntry(60,130));
        cambioTasa.add(new BarEntry(70,323));
        cambioTasa.add(new BarEntry(88,224));
        cambioTasa.add(new BarEntry(90,192));
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
        if(tasa.getMonto() > 1)
        {
            iconoSubida.setVisibility(View.VISIBLE);
            
            tasaDia.setText(""+ tasa.getMonto());
            tasaAnterior.setText(""+ anterior.getMonto());
            
            float porcentaje = tasa.getPorcentajeCambio();
            if(porcentaje > 0)
            {
                iconoSubida.setImageResource(R.drawable.ic_arrow_drop_up_24px);
            }
            else if(porcentaje < 0)
            {
                iconoSubida.setImageResource(R.drawable.ic_arrow_drop_down_24px);
            }
        }
        else
        {
            iconoSubida.setVisibility(View.GONE);
            tasaDia.setText("-");
            tasaAnterior.setText("-");
        }
    }
}

