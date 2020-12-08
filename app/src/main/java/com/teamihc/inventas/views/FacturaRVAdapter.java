package com.teamihc.inventas.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.ArticuloPxQ;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FacturaRVAdapter extends RecyclerView.Adapter<FacturaRVAdapter.FacturaAdapter>
{
    
    ArrayList<ArticuloPxQ> listaProductos;
    
    public FacturaRVAdapter(ArrayList<ArticuloPxQ> listaProductos)
    {
        this.listaProductos = listaProductos;
    }
    
    @NonNull
    @Override
    public FacturaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_producto, parent, false);
        return new FacturaAdapter(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FacturaAdapter holder, int position)
    {
        
        holder.asignarDatos(listaProductos.get(position));
    }
    
    @Override
    public int getItemCount()
    {
        //return listaProductos.size();
        return 0;
    }
    
    public class FacturaAdapter extends RecyclerView.ViewHolder
    {
        CardView cardView;
        
        public FacturaAdapter(@NonNull View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.info_producto);
        }
        
        public void asignarDatos(@NotNull ArticuloPxQ articulo)
        {
            ImageView imagenProd = (ImageView) cardView.findViewById(R.id.imagenProd);
            TextView descripcion = (TextView) cardView.findViewById(R.id.descripcion);
            TextView monto = (TextView) cardView.findViewById(R.id.monto);
            
            
            //imagenProd.setImageResource();
            descripcion.setText(articulo.toString());
            monto.setText(Float.toString(articulo.getSubTotal()));
        }
    }
}
