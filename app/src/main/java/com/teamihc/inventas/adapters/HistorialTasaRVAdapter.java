package com.teamihc.inventas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Tasa;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Karen
 */
public class HistorialTasaRVAdapter extends RecyclerView.Adapter<HistorialTasaRVAdapter.HistorialTasaAdapter>
{
    private ArrayList<Tasa> listaTasas;
    
    /**
     * Crea una instancia de RecyclerView que contendrá una lista con el cambio de las tasas.
     *
     * @param listaTasas es la lista que contiene a cada una de las tasas registradas en el tiempo.
     */
    public HistorialTasaRVAdapter(ArrayList<Tasa> listaTasas)
    {
        this.listaTasas = listaTasas;
    }
    
    @NonNull
    @Override
    public HistorialTasaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_tasa, parent, false);
        return new HistorialTasaAdapter(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull HistorialTasaAdapter holder, int position)
    {
        holder.asignarDatos(listaTasas.get(position));
    }
    
    @Override
    public int getItemCount()
    {
        return listaTasas.size();
    }
    
    public class HistorialTasaAdapter extends RecyclerView.ViewHolder
    {
        CardView cardView;
        
        public HistorialTasaAdapter(@NonNull View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.info_tasa);
        }
        
        public void asignarDatos(@NotNull Tasa tasa)
        {
            ImageView imagenCambio = (ImageView) cardView.findViewById(R.id.cambio);
            TextView monto = (TextView) cardView.findViewById(R.id.cantidadTasa);
            TextView fecha = (TextView) cardView.findViewById(R.id.fecha);
            TextView porcentaje = (TextView) cardView.findViewById(R.id.porcentaje);
            
            String fechaHora =
                    Herramientas.FORMATO_FECHA_FRONT.format(tasa.getFechaHora())
                    + ", " +
                    Herramientas.FORMATO_TIEMPO_FRONT.format(tasa.getFechaHora());
            float porcentajeCambio = tasa.getPorcentajeCambio();
            
            monto.setText(Herramientas.formatearMonedaBs(tasa.getMonto()));
            fecha.setText(fechaHora);
            porcentaje.setText(Herramientas.formatearPorcentaje(Math.abs(porcentajeCambio)));
            
            if(porcentajeCambio > 99)
            {
                porcentaje.setVisibility(View.GONE);
            }
            
            if (porcentajeCambio == 0)
            {
                imagenCambio.setVisibility(View.INVISIBLE);
            }
            else if (porcentajeCambio > 0)
            {
                imagenCambio.setImageResource(R.drawable.ic_arrow_drop_up_24px);
            }
            else if (porcentajeCambio < 0)
            {
                imagenCambio.setImageResource(R.drawable.ic_arrow_drop_down_24px);
            }
            
        }
    }
}
