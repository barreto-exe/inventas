package com.teamihc.inventas.views;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.activities.MainActivity;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.fragments.ListaProductosVentaFragment;

import java.util.ArrayList;

public abstract class ListaProductosRecyclerViewAdapter
        extends RecyclerView.Adapter<ListaProductosRecyclerViewAdapter.ListaProductosAdapter>
        implements View.OnClickListener, View.OnLongClickListener {

    private ArrayList<Articulo> listaArticulos;

    public ListaProductosRecyclerViewAdapter(ArrayList<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
    
    @NonNull
    @Override
    public ListaProductosAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_producto, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ListaProductosAdapter(view);
    }
    

    @Override
    public void onBindViewHolder(@NonNull ListaProductosAdapter holder, int position)
    {
        holder.asignarDatos(listaArticulos.get(position));
    }
    
    @Override
    public int getItemCount()
    {
        return listaArticulos.size();
    }

    public class ListaProductosAdapter extends RecyclerView.ViewHolder
    {
        
        CardView cardView;
        
        public ListaProductosAdapter(@NonNull View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.info_producto);
        }
        
        public void asignarDatos(Articulo articulo) {
            ImageView imagenProd = (ImageView) cardView.findViewById(R.id.imagenProd);
            TextView descripcion = (TextView) cardView.findViewById(R.id.descripcion);
            TextView precioBsS = (TextView) cardView.findViewById(R.id.precioBsS);
            TextView cantidadStock = (TextView) cardView.findViewById(R.id.cantidadStock);
            TextView costoD = (TextView) cardView.findViewById(R.id.costoD);
            TextView precioD = (TextView) cardView.findViewById(R.id.precioD);
            
            //imagenProd.setImageResource();
            descripcion.setText(articulo.getDescripcion());
            precioBsS.setText("" + articulo.getPrecioBs());
            cantidadStock.setText("" + articulo.getCantidad());
            costoD.setText("" + articulo.getCosto());
            precioD.setText("" + articulo.getPrecio());
        }
    }
}
