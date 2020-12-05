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
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;


public class InventarioFragment extends Fragment
{
    ListaProductosRecyclerViewAdapter.ListaProductosAdapter listaProductosAdapter;
    RecyclerView recyclerView;
    //ArrayList de los productos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_inventario, container, false);
        recyclerView=view.findViewById(R.id.productos_inventarioRV);
        //lista de productos =new Arraylist();
        //Aqui se debe cargar la lista

        //Y luego mostrar los datos
        //mostrarDatos();
        return view;
        
    }
    public void cargarLista(){
        //aqui se cargan los datos de la lista
    }

    public void mostrarDatos(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
     //   listaProductosAdapter=new ListaProductosRecyclerViewAdapter.ListaProductosAdapter(getContext());
        // recyclerView.setAdapter(listaProductosAdapter);
        //se le  debe agregar el onclick
    }
}

