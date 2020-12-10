package com.teamihc.inventas.fragments;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class VentasFragment extends Fragment
{
    View view;
    RecyclerView recyclerView;
    ResumenVentaRVAdapter.ResumenVentaAdapter listaVentaAdapter;
    ArrayList<Venta> listaVentas;
    ResumenVentaRVAdapter adapter;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_ventas, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.ventasDelDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        
        listaVentas = new ArrayList<Venta>();
        Venta.cargarVentasEnLista(listaVentas, Calendar.getInstance().getTime());
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
        Venta.cargarVentasEnLista(listaVentas, Calendar.getInstance().getTime());
        adapter.notifyDataSetChanged();
    
        refrescarGananciasDelDia();
    }
    
    private void refrescarGananciasDelDia()
    {
        //Colocar etiqueta de ganancias del día
        ((TextView)view.findViewById(R.id.gananciasDelDia)).setText(Herramientas.formatearMonedaDolar(Estadisticas.gananciasPorDia(Calendar.getInstance().getTime())));
    }
}

