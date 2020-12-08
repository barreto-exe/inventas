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
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;
import com.teamihc.inventas.views.ListaProductosInventarioRecyclerViewAdapter;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;


public class InventarioFragment extends Fragment
{
    private RecyclerView recyclerView;
    private ArrayList<Articulo> listaArticulos;
    private ListaProductosInventarioRecyclerViewAdapter adapter;
    
    //ArrayList de los productos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.productos_inventarioRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        listaArticulos = new ArrayList<Articulo>();

        Articulo.cargarInventarioEnLista(listaArticulos);
        adapter = new ListaProductosInventarioRecyclerViewAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);
        
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        listaArticulos.clear();
        Articulo.cargarInventarioEnLista(listaArticulos);
        adapter.notifyDataSetChanged();
    }
}

