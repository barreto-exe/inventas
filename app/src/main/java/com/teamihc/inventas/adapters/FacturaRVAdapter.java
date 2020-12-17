package com.teamihc.inventas.adapters;

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
import com.teamihc.inventas.backend.entidades.ArticuloPxQ;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.teamihc.inventas.backend.Herramientas.formatearMonedaDolar;
import static com.teamihc.inventas.backend.Herramientas.getCompressedBitmapImage;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_producto_factura, parent, false);
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
        return listaProductos.size();
    }
    
    public class FacturaAdapter extends RecyclerView.ViewHolder
    {
        private View view;
        CardView cardView;
        
        public FacturaAdapter(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            cardView = (CardView) itemView.findViewById(R.id.info_producto);
        }
        
        public void asignarDatos(@NotNull ArticuloPxQ articulo)
        {
            ImageView imagenProd = (ImageView) cardView.findViewById(R.id.imagenProd);
            TextView descripcion = (TextView) cardView.findViewById(R.id.descripcion);
            TextView cantidadStock = (TextView) cardView.findViewById(R.id.cantidadStock);
            TextView subtotal = (TextView) cardView.findViewById(R.id.subtotal);

            if (!articulo.getArticulo().getImagen_path().equals(""))
            {
                Glide.with(view).load(articulo.getArticulo().getImagen_path()).into(imagenProd);
            }
            descripcion.setText(articulo.getArticulo().getDescripcion());
            cantidadStock.setText("" + articulo.getCantidad());
            subtotal.setText(formatearMonedaDolar(articulo.getArticulo().getPrecio() * articulo.getCantidad()));
        }
    }
}
