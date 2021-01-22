package com.teamihc.inventas.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.FacturaActivity;
import com.teamihc.inventas.activities.HistorialVentasActivity;
import com.teamihc.inventas.activities.MainActivity;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.ArticuloPxQ;
import com.teamihc.inventas.backend.entidades.Venta;

import java.util.ArrayList;

public class HistorialVentaRVAdapter extends RecyclerView.Adapter<HistorialVentaRVAdapter.HistorialVentaAdapter> implements View.OnClickListener
{
    private ArrayList<Venta> listaVentas;

    /**
     * Crea una instancia de RecyclerView que contendrá una lista con todas las ventas registradas.
     *
     * @param listaVentas es la lista que contiene a cada una de las ventas registradas.
     */
    public HistorialVentaRVAdapter(ArrayList<Venta> listaVentas) { this.listaVentas = listaVentas; }

    @NonNull
    @Override
    public HistorialVentaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_venta_historial, parent, false);
        view.setOnClickListener(this);
        return new HistorialVentaRVAdapter.HistorialVentaAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialVentaAdapter holder, int position)
    {
        holder.asignarDatos(listaVentas.get(position));
    }

    @Override
    public int getItemCount()
    {
        return listaVentas.size();
    }

    @Override
    public void onClick(View view)
    {
        TextView id = (TextView) view.findViewById(R.id.idVentaH);
        HistorialVentasActivity mainActivity = ((HistorialVentasActivity) view.getContext());
        Intent intent = new Intent(mainActivity, FacturaActivity.class);
        intent.putExtra("id", id.getText().toString());
        mainActivity.startActivity(intent);
    }

    public class HistorialVentaAdapter extends RecyclerView.ViewHolder
    {
        CardView cardView;

        public HistorialVentaAdapter(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)  itemView.findViewById(R.id.infoVentaHistorial);
        }

        public void asignarDatos(Venta venta)
        {
            TextView fecha = (TextView) cardView.findViewById(R.id.diaFechaVentaH);
            TextView hora = (TextView) cardView.findViewById(R.id.horaVentaH);
            TextView ventaBsS = (TextView) cardView.findViewById(R.id.ventaBsSH);
            TextView resumen = (TextView) cardView.findViewById(R.id.resumenVentaH);
            TextView ventaD = (TextView) cardView.findViewById(R.id.ventaDH);
            TextView id = (TextView) cardView.findViewById(R.id.idVentaH);

            float monto = venta.obtenerTotalDolares();
            float conversion = venta.obtenerTotalBsS();
            ArrayList<ArticuloPxQ> listaArticulos = venta.getCarrito().getCarrito();
            String resumenStr = "";
            int cantArticulos = listaArticulos.size();
            for (int i = 0; i < cantArticulos; i++)
            {
                resumenStr +=
                        listaArticulos.get(i).getCantidad() + " " +
                                listaArticulos.get(i).getArticulo().getDescripcion();
                if(i < cantArticulos - 1)
                {
                    resumenStr += ", ";
                }
                else
                {
                    resumenStr += ".";
                }
            }

            fecha.setText(Herramientas.formatearDiaFecha(venta.getFechaHora()));
            hora.setText(Herramientas.FORMATO_TIEMPO_FRONT.format(venta.getFechaHora()));
            ventaD.setText(Herramientas.formatearMonedaDolar((monto)));
            ventaBsS.setText(Herramientas.formatearMonedaBs(conversion));
            id.setText(Integer.toString(venta.obtenerId()));
            resumen.setText(resumenStr);
        }
    }
}