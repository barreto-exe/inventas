package com.teamihc.inventas.adapters.listaproductos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;

import java.util.ArrayList;

import static com.teamihc.inventas.backend.Herramientas.formatearMonedaDolar;
import static com.teamihc.inventas.backend.Herramientas.formatearMonedaBs;
import static com.teamihc.inventas.backend.Herramientas.getCompressedBitmapImage;

public abstract class ListaProductosRVAdapter
        extends RecyclerView.Adapter<ListaProductosRVAdapter.ListaProductosAdapter>
        implements View.OnClickListener, View.OnLongClickListener
{
    
    protected int layoutId;
    protected ArrayList<Articulo> listaArticulos;
    protected CardView cardView;
    
    public ListaProductosRVAdapter(ArrayList<Articulo> listaArticulos, int layoutId)
    {
        this.listaArticulos = listaArticulos;
        this.layoutId = layoutId;
    }
    
    @NonNull
    @Override
    public ListaProductosAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
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
        private View view;
        
        public ListaProductosAdapter(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            cardView = (CardView) itemView.findViewById(R.id.info_producto);
        }
        
        public void asignarDatos(Articulo articulo)
        {
            ImageView imagenProd = (ImageView) cardView.findViewById(R.id.imagenProd);
            TextView descripcion = (TextView) cardView.findViewById(R.id.descripcion);
            TextView precioBsS = (TextView) cardView.findViewById(R.id.precioBsS);
            TextView cantidadStock = (TextView) cardView.findViewById(R.id.cantidadStock);
            TextView costoD = (TextView) cardView.findViewById(R.id.costoD);
            TextView precioD = (TextView) cardView.findViewById(R.id.precioD);
            
            if (imagenProd != null)
            {
                if (!articulo.getImagen_path().equals("")){
                    Glide.with(view).load(articulo.getImagen_path()).into(imagenProd);
                }
            }
            if (descripcion != null)
            {
                descripcion.setText(articulo.getDescripcion());
            }
            if (precioBsS != null)
            {
                precioBsS.setText(formatearMonedaBs(articulo.getPrecioBs()));
            }
            if (cantidadStock != null)
            {
                cantidadStock.setText("" + articulo.getCantidad());
            }
            if (costoD != null)
            {
                costoD.setText(formatearMonedaDolar(articulo.getCosto()));
            }
            if (precioD != null)
            {
                precioD.setText(formatearMonedaDolar(articulo.getPrecio()));
            }
        }
    }
}
