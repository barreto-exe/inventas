package com.teamihc.inventas.fragments;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Venta;
import com.teamihc.inventas.views.ResumenVentaRVAdapter;

import java.util.ArrayList;


public class VentasFragment extends Fragment
{
    RecyclerView recyclerView;
    ResumenVentaRVAdapter.ResumenVentaAdapter listaVentaAdapter;
    ResumenVentaRVAdapter adapter;
    ArrayList<Venta> listaVentas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ventas, container, false);
        //Falta codigo con respecto al llenado de la lista de ventas
        recyclerView = (RecyclerView) view.findViewById(R.id.ventasDelDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        // aqui cargas la lista
        adapter= new ResumenVentaRVAdapter(listaVentas);
        recyclerView.setAdapter(adapter);
        return view;
    }


    //Para que se actualice el recycler
    @Override
    public void onResume() {
        super.onResume();
        //Falta codigo con respecto al llenado de la lista de ventas, ver InventarioFragment
    }
}

