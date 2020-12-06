package com.teamihc.inventas.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.adapters.InventarioAdapter;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;


public class InventarioFragment extends Fragment
{
    ListaProductosRecyclerViewAdapter.ListaProductosAdapter listaProductosAdapter;
    RecyclerView recyclerView;
    private ArrayList<Articulo> listaArticulos;
    
    //ArrayList de los productos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.productos_inventarioRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        
        listaArticulos = new ArrayList<Articulo>();
        
        cargarLista();
        mostrarDatos();
        
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        //Refrescar inventario al volver al fragment
        cargarLista();
        mostrarDatos();
    }
    
    //como actualizar la lista??
    //Como validar???
    public void cargarLista()
    {
        listaArticulos.clear();
        String query = "SELECT * FROM v_articulos ORDER BY descripcion ASC";
        DBOperacion op = new DBOperacion(query);
        DBMatriz resultado = op.consultar();
        
        while (resultado.leer())
        {
            Articulo articulo = new Articulo(
                    (String) resultado.getValor("descripcion"),
                    (Float) resultado.getValor("costo_unitario"),
                    (Float) resultado.getValor("precio_venta"),
                    (Integer) resultado.getValor("cantidad"),
                    "0000");
            listaArticulos.add(articulo);
        }
    }
    
    public void mostrarDatos()
    {
        //recyclerView.clearFocus();
        InventarioAdapter adapter = new InventarioAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);
    }
}

