package com.teamihc.inventas.views;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.activities.MainActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

public class ListaProductosCarritoRecyclerViewAdapter extends ListaProductosRecyclerViewAdapter{

    public ListaProductosCarritoRecyclerViewAdapter(ArrayList<Articulo> listaArticulos) {
        super(listaArticulos);
    }

    @Override
    public void onClick(View v)
    {
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);

    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }
}
