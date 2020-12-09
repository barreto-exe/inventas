package com.teamihc.inventas.views;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.activities.FacturaActivity;
import com.teamihc.inventas.activities.MainActivity;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.backend.entidades.Venta;
import com.teamihc.inventas.dialogs.SeleccionarCantidadDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ResumenVentaRVAdapter extends RecyclerView.Adapter<ResumenVentaRVAdapter.ResumenVentaAdapter> implements View.OnClickListener
{
    private View.OnClickListener listener;
    private ArrayList<Venta> listaVenta;
    Dialog dialog;
    
    
    //Constructor
    public ResumenVentaRVAdapter(ArrayList<Venta> listaVenta)
    {
        this.listaVenta = listaVenta;
    }
    
    @NonNull
    @Override
    public ResumenVentaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_venta, parent, false);
        view.setOnClickListener(this);
        return new ResumenVentaAdapter(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ResumenVentaAdapter holder, int position)
    {
        holder.asignarDatos(listaVenta.get(position));
    }
    
    @Override
    public int getItemCount()
    {
        return listaVenta.size();
    }
    
    @Override
    public void onClick(View view)
    {
        FacturaActivity facturaActivity = (FacturaActivity) view.getContext();
        TextView id = (TextView) view.findViewById(R.id.idVenta);

        Venta venta = Venta.
        Articulo articulo = Articulo.obtenerInstancia(descripcion.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putString("modo", "edicion");
        SeleccionarCantidadDialogFragment dialog = new SeleccionarCantidadDialogFragment(carritoActivity, articulo);
        dialog.setArguments(bundle);
        dialog.show(carritoActivity.getFragmentManager(), null);
    }
    
    /*  @Override
      public void onClick(View v) {
          //Lleva a la factura, no estoy muy segura de esto, gustavo puede que sepa un chin mas
          MainActivity mainActivity = ((MainActivity) v.getContext());
          Intent intent = new Intent(mainActivity,FacturaActivity.class);
          //intent.putExtra("descripcion", descripcion.getText().toString());
          mainActivity.startActivity(intent);
      }
  */
    public class ResumenVentaAdapter extends RecyclerView.ViewHolder
    {
        
        CardView cardView;
        TextView fecha;
        
        public ResumenVentaAdapter(@NonNull View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.infoVenta);
        }
        
        public void asignarDatos(Venta venta)
        {
            TextView hora = (TextView) cardView.findViewById(R.id.hora);
            TextView ventaBsS = (TextView) cardView.findViewById(R.id.ventaBsS);
            TextView resumen = (TextView) cardView.findViewById(R.id.resumen);
            TextView ventaD = (TextView) cardView.findViewById(R.id.ventaD);
            TextView id = (TextView) cardView.findViewById(R.id.idVenta);
            
            float monto = venta.getCarrito().obtenerTotal();
            float conversion = monto * Tasa.obtenerTasa().getMonto();
            hora.setText(new SimpleDateFormat(Herramientas.FORMATO_TIEMPO_STRING).format(venta.getFechaHora()));
            ventaD.setText(Float.toString(monto));
            ventaBsS.setText(Float.toString(conversion));
            id.setText(Integer.toString(venta.obtenerId()));

            fecha = (TextView) cardView.findViewById(R.id.fechaActual);
        }
    }
}
