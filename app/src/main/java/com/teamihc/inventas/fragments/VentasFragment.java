package com.teamihc.inventas.fragments;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Estadisticas;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Venta;
import com.teamihc.inventas.adapters.ResumenVentaRVAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class VentasFragment extends Fragment
{
    View view;
    RecyclerView recyclerView;
    LinearLayout bienvenida, contenido;
    ResumenVentaRVAdapter.ResumenVentaAdapter listaVentaAdapter;
    ArrayList<Venta> listaVentas;
    ResumenVentaRVAdapter adapter;
    Date fechaConsultada;
    
    public void setFechaConsultada(Date fechaConsultada)
    {
        this.fechaConsultada = fechaConsultada;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_ventas, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.ventasDelDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        bienvenida = (LinearLayout) view.findViewById(R.id.bienvenida_ventas);
        contenido = (LinearLayout) view.findViewById(R.id.contenido_ventas);
        
        listaVentas = new ArrayList<Venta>();
        Venta.cargarVentasDiaEnLista(listaVentas, fechaConsultada);
        adapter = new ResumenVentaRVAdapter(listaVentas);
        recyclerView.setAdapter(adapter);
        
        refrescarGananciasDelDia();
        
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        listaVentas.clear();
        Venta.cargarVentasDiaEnLista(listaVentas, fechaConsultada);
        adapter.notifyDataSetChanged();
        
        refrescarGananciasDelDia();
    }
    
    private void refrescarGananciasDelDia()
    {
        ((TextView) view.findViewById(R.id.fechaActual)).setText(Herramientas.formatearDiaFecha(fechaConsultada));
        ((TextView) view.findViewById(R.id.gananciasDelDia)).setText(Herramientas.formatearMonedaDolar(Estadisticas.gananciasPorDia(fechaConsultada)));
        
        colocarBienvenida();
    }
    
    private void colocarBienvenida()
    {
        if (listaVentas.isEmpty())
        {
            bienvenida.setVisibility(View.VISIBLE);
            contenido.setVisibility(View.GONE);
        }
        else
        {
            bienvenida.setVisibility(View.GONE);
            contenido.setVisibility(View.VISIBLE);
        }
    }
}

