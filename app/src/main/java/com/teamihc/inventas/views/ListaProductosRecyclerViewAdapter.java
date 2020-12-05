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

public class ListaProductosRecyclerViewAdapter extends RecyclerView.Adapter<ListaProductosRecyclerViewAdapter.ListaProductosAdapter> implements View.OnClickListener {
//-----------Variables---------------
    LayoutInflater inflater;

    private View.OnClickListener listener;
    //aqui se debe poner una lista para vaciar el contenido en cada cardView


    //-------------------------------------------------------------------------------------
    //constructor, en este se le debe pasar tambien la lista por parametro
    public ListaProductosRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        //this.lista que vas a crear= la lista que pasas por parametro
    }


    @NonNull
    @Override
    public ListaProductosAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_producto, parent, false);
        view.setOnClickListener(this);
        return new ListaProductosAdapter(view);
    }

    //Esto lo debes cambiar de acuerdo a tu lista, lo que está despues del ultimo punto representa el campo del nodo de la lista
    //ejemplo: holder.imagenProd.setImageResource(lista.get(position).fotoProducto); donde foto producto es la foto que está guardada en la base de datos
    //y que luego de ser consultada y vaciada en la lista se coloca ahí
    @Override
    public void onBindViewHolder(@NonNull ListaProductosAdapter holder, int position) {
        //Aqui de acuerdo a lo que se extraiga de una clase que contenga las cosas de los productos se debe "vaciar" en lo que está en layout info_producto
        //holder.imagenProd.setImageResource(lista.get(position).);
        //holder.descripcion.setText(lista.get(position).);
        //holder.cantidadStock.setText(lista.get(position).);
        //holder.precioAmostrrBs.setText(lista.get(position).);
        //holder.precioD.setText(lista.get(position).);
        //holder.costoD.setText(lista.get(position).);
    }


    @Override
    public int getItemCount() {
        return 0;
        //debe retornar eltamaño de la lista
    }



    //Esto es para que se pueda editar la cantidad del stock, se complementa con setOnClick()
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public void setOnClick(View.OnClickListener view){
        this.listener=view;
    }

    //esto se queda así
    public class ListaProductosAdapter extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView descripcion, cantidad, precioBsS, precioD, costoD;

        public ListaProductosAdapter(@NonNull View itemView) {
            super(itemView);
            foto=itemView.findViewById(R.id.imagenProd);
            descripcion=itemView.findViewById(R.id.descripcion);
            cantidad=itemView.findViewById(R.id.cantidadStock);
            precioBsS=itemView.findViewById(R.id.precioAmostrarBs);
            precioD=itemView.findViewById(R.id.precioD);
            costoD=itemView.findViewById(R.id.costoD);
        }
    }
}
