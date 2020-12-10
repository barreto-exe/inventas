package com.teamihc.inventas.adapters.listaproductos;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.dialogs.SeleccionarCantidadDialogFragment;

import java.util.ArrayList;

public class CarritoElegirRVAdapter extends ListaProductosRVAdapter
{
    
    public CarritoElegirRVAdapter(ArrayList<Articulo> listaArticulos, int layoutId)
    {
        super(listaArticulos, layoutId);
    }
    
    @Override
    public void onClick(View v)
    {
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        CarritoActivity carritoActivity = ((CarritoActivity) v.getContext());
        Articulo articulo = Articulo.obtenerInstancia(descripcion.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putString("modo", "creacion");
        SeleccionarCantidadDialogFragment dialog = new SeleccionarCantidadDialogFragment(carritoActivity, articulo);
        dialog.setArguments(bundle);
        dialog.show(carritoActivity.getFragmentManager(), null);
    }
    
    @Override
    public boolean onLongClick(View v)
    {
        return false;
    }
}
