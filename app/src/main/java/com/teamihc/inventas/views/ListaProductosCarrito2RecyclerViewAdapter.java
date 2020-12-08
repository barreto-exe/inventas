package com.teamihc.inventas.views;

import android.view.View;

import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

public class ListaProductosCarrito2RecyclerViewAdapter extends ListaProductosRecyclerViewAdapter{

    public ListaProductosCarrito2RecyclerViewAdapter(ArrayList<Articulo> listaArticulos) {
        super(listaArticulos);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }
}
