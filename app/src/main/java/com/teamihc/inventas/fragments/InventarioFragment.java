package com.teamihc.inventas.fragments;

import android.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.adapters.listaproductos.InventarioRVAdapter;

import java.io.File;
import java.util.ArrayList;


public class InventarioFragment extends Fragment
{
    private RecyclerView recyclerView;
    private LinearLayout bienvenida;
    private ArrayList<Articulo> listaArticulos;
    private InventarioRVAdapter adapter;
    
    //ArrayList de los productos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.productos_inventarioRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        bienvenida = (LinearLayout) view.findViewById(R.id.bienvenida_inventario);
        listaArticulos = new ArrayList<Articulo>();
        
        Articulo.cargarInventarioEnLista(listaArticulos);
        adapter = new InventarioRVAdapter(listaArticulos, R.layout.view_info_producto);
        recyclerView.setAdapter(adapter);
        
        ColocarBienvenida();
        
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        listaArticulos.clear();
        adapter.notifyDataSetChanged();
        adapter = new InventarioRVAdapter(listaArticulos, R.layout.view_info_producto);
        recyclerView.setAdapter(adapter);
        Articulo.cargarInventarioEnLista(listaArticulos);
        
        ColocarBienvenida();
    }
    
    private void ColocarBienvenida()
    {
        if (listaArticulos.isEmpty())
        {
            bienvenida.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            bienvenida.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}

