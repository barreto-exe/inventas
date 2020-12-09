package com.teamihc.inventas.views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;

import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.dialogs.SeleccionarCantidadDialogFragment;

import java.util.ArrayList;

public class ListaProductosCarritoRVAdapter extends ListaProductosRVAdapter
{
    
    public ListaProductosCarritoRVAdapter(ArrayList<Articulo> listaArticulos, int layoutId)
    {
        super(listaArticulos, layoutId);
    }
    
    @Override
    public void onClick(View v)
    {
        CarritoActivity carritoActivity = (CarritoActivity) v.getContext();
        CardView cardView = (CardView) v.findViewById(R.id.info_producto);
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        
        if (carritoActivity.isModoBorrar())
        {
            TextView modo = (TextView) v.findViewById(R.id.modo);
            if (modo.getText().toString().equals("0"))
            {
                cardView.setBackgroundColor(v.getResources().getColor(R.color.rosado));
                carritoActivity.agregarABasura(descripcion.getText().toString());
                modo.setText("1");
            }
            else
            {
                cardView.setBackgroundColor(v.getResources().getColor(R.color.white));
                carritoActivity.quitarDeBasura(descripcion.getText().toString());
                modo.setText("0");
            }
        }
        else
        {
            Articulo articulo = Articulo.obtenerInstancia(descripcion.getText().toString());
            Bundle bundle = new Bundle();
            bundle.putString("modo", "edicion");
            SeleccionarCantidadDialogFragment dialog = new SeleccionarCantidadDialogFragment(carritoActivity, articulo);
            dialog.setArguments(bundle);
            dialog.show(carritoActivity.getFragmentManager(), null);
        }
    }
    
    @Override
    public boolean onLongClick(View v)
    {
        CardView cardView = (CardView) v.findViewById(R.id.info_producto);
        cardView.setBackgroundColor(v.getResources().getColor(R.color.rosado));
        TextView modo = (TextView) v.findViewById(R.id.modo);
        modo.setText("1");
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        CarritoActivity carritoActivity = (CarritoActivity) v.getContext();
        carritoActivity.modoBorrar(descripcion.getText().toString());
        return true;
    }
}
