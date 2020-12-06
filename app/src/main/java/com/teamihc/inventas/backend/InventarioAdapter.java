package com.teamihc.inventas.backend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;

import java.util.ArrayList;

public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.ViewHolderDatos> {

    ArrayList<Articulo> listaDatos;

    public InventarioAdapter(ArrayList<Articulo> listaDatos) {
        this.listaDatos = listaDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_producto,
                null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.info_producto);
        }

        public void asignarDatos(Articulo articulo) {
            TextView descripcion = (TextView) cardView.findViewById(R.id.descripcion);
            TextView precioBsS =  (TextView)cardView.findViewById(R.id.precioBsS);
            TextView cantidadStock = (TextView)cardView.findViewById(R.id.cantidadStock);
            TextView costoD = (TextView)cardView.findViewById(R.id.costoD);
            TextView precioD = (TextView)cardView.findViewById(R.id.precioD);

            descripcion.setText(articulo.getDescripcion());
            precioBsS.setText("" + articulo.getPrecio());
            cantidadStock.setText("" + articulo.getCantidad());
            costoD.setText("" + articulo.getCosto());
            precioD.setText("" + articulo.getPrecio());
        }
    }
}
