package com.teamihc.inventas.views;

import android.view.View;
import android.widget.TextView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

public class ListaProductosCarrito2RVAdapter extends ListaProductosRVAdapter
{

    public ListaProductosCarrito2RVAdapter(ArrayList<Articulo> listaArticulos) {
        super(listaArticulos);
    }


    @Override
    public void onClick(View v) {
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        CarritoActivity carritoActivity = ((CarritoActivity) v.getContext());
        carritoActivity.cargarArticulo(descripcion.getText().toString());
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
