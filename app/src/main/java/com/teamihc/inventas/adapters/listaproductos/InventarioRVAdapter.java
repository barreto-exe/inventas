package com.teamihc.inventas.adapters.listaproductos;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.activities.MainActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

public class InventarioRVAdapter extends ListaProductosRVAdapter
{
    
    public InventarioRVAdapter(ArrayList<Articulo> listaArticulos, int layoutId)
    {
        super(listaArticulos, layoutId);
    }
    
    @Override
    public void onClick(View v)
    {
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        MainActivity mainActivity = ((MainActivity) v.getContext());
        Intent intent = new Intent(mainActivity, CrearProductoActivity.class);
        intent.putExtra("descripcion", descripcion.getText().toString());
        mainActivity.startActivity(intent);
    }
    
    @Override
    public boolean onLongClick(View v)
    {
        return false;
    }
}
