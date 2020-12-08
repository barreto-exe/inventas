package com.teamihc.inventas.views;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

public class ListaProductosCarrito2RecyclerViewAdapter extends ListaProductosRecyclerViewAdapter{

    public ListaProductosCarrito2RecyclerViewAdapter(ArrayList<Articulo> listaArticulos) {
        super(listaArticulos);
    }


    @Override
    public void onClick(View v) {
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        CarritoActivity carritoActivity = ((CarritoActivity) v.getContext());
        carritoActivity.hideFragment();
        carritoActivity.cargarArticulo(descripcion.getText().toString());
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
