package com.teamihc.inventas.fragments;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Tasa;


public class TasasFragment extends Fragment
{
    private View view;
    private TextView tasaDia;
    private TextView tasaAnterior;
    private ImageView iconoSubida;
    
    //aqui se vacia la info de la tasa actual y de la estadistica del cambio de tasas
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tasas, container, false);
        refrescarTasaDia();
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        refrescarTasaDia();
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

