package com.teamihc.inventas.views;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;

import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.dialogs.SeleccionarCantidadDialogFragment;

import java.util.ArrayList;

public class ListaProductosCarritoRecyclerViewAdapter extends ListaProductosRecyclerViewAdapter{

    public ListaProductosCarritoRecyclerViewAdapter(ArrayList<Articulo> listaArticulos) {
        super(listaArticulos);
    }

    @Override
    public void onClick(View v)
    {
        CarritoActivity carritoActivity = (CarritoActivity)v.getContext();
        CardView cardView = (CardView)v.findViewById(R.id.info_producto);
        TextView descripcion = (TextView)v.findViewById(R.id.descripcion);

        if (carritoActivity.isModoBorrar()){
            TextView modo = (TextView)v.findViewById(R.id.modo);
            if(modo.getText().toString().equals("0")){
                cardView.setBackgroundColor(v.getResources().getColor(R.color.rosado));
                carritoActivity.agregarABasura(descripcion.getText().toString());
                modo.setText("1");
            }else{
                cardView.setBackgroundColor(v.getResources().getColor(R.color.white));
                carritoActivity.quitarDeBasura(descripcion.getText().toString());
                modo.setText("0");
            }
        }else{
            Articulo articulo = Articulo.obtenerInstancia(descripcion.getText().toString());
            new SeleccionarCantidadDialogFragment(carritoActivity, articulo).show(carritoActivity.getFragmentManager(), null);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        CardView cardView = (CardView)v.findViewById(R.id.info_producto);
        cardView.setBackgroundColor(v.getResources().getColor(R.color.rosado));
        TextView modo = (TextView)v.findViewById(R.id.modo);
        modo.setText("1");
        TextView descripcion = (TextView)v.findViewById(R.id.descripcion);
        CarritoActivity carritoActivity = (CarritoActivity)v.getContext();
        carritoActivity.modoBorrar(descripcion.getText().toString());
        return true;
    }
}
