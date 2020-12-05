package com.teamihc.inventas.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;

public class HistorialTasaRecyclerViewAdapter extends RecyclerView.Adapter<HistorialTasaRecyclerViewAdapter.HistorialTasaAdapter>
{
    Context context;
    
    //List<> aqui se debe poner un lista de los cambios del dolar
    @NonNull
    @Override
    public HistorialTasaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.id.infoTasa, parent, false);
        return new HistorialTasaAdapter(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull HistorialTasaAdapter holder, int position)
    {
        //Aqui de acuerdo a lo que se extraiga de una clase que contenga las cosas de las tasas se debe "vaciar" en lo que está en layout info_tasa
        //holder.cambio.setImageResource(lista.get(position).);
    }
    
    @Override
    public int getItemCount()
    {
        return 0;
    }
    
    //Aqui se deben pasar las cosas para que se rellenen los valores del cardView
    public class HistorialTasaAdapter extends RecyclerView.ViewHolder
    {
        ImageView cambio;
        TextView tasa, porcentaje, fecha;
        
        public HistorialTasaAdapter(@NonNull View itemView)
        {
            super(itemView);
            cambio = itemView.findViewById(R.id.cambio);
            tasa = itemView.findViewById(R.id.cantidadTasa);
            porcentaje = itemView.findViewById(R.id.porcentaje);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }
}
