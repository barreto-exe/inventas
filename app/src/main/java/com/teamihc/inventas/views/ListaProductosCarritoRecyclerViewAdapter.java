package com.teamihc.inventas.views;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;

import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

public class ListaProductosCarritoRecyclerViewAdapter extends ListaProductosRecyclerViewAdapter{

    public ListaProductosCarritoRecyclerViewAdapter(ArrayList<Articulo> listaArticulos) {
        super(listaArticulos);
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public boolean onLongClick(View v) {
        CardView cardView = (CardView)
        return true;
    }
}
