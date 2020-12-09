package com.teamihc.inventas.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.ArticuloPxQ;
import com.teamihc.inventas.backend.entidades.Carrito;
import com.teamihc.inventas.dialogs.SeleccionarCantidadDialogFragment;

import static com.teamihc.inventas.backend.Herramientas.formatearMonedaDolar;

public class ListaProductosCarritoRVAdapter extends RecyclerView.Adapter<ListaProductosCarritoRVAdapter.ListaProductosAdapter>
        implements View.OnClickListener, View.OnLongClickListener
{
    private Carrito carrito;
    private CardView cardView;

    public ListaProductosCarritoRVAdapter(Carrito carrito) {
        this.carrito = carrito;
    }

    @NonNull
    @Override
    public ListaProductosAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_producto_factura, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ListaProductosAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProductosCarritoRVAdapter.ListaProductosAdapter holder, int position) {
        holder.asignarDatos(carrito.getCarrito().get(position));
    }

    @Override
    public int getItemCount()
    {
        return carrito.getCarrito().size();
    }

    @Override
    public boolean onLongClick(View v)
    {
        CardView cardView = (CardView) v.findViewById(R.id.info_producto);
        cardView.setBackgroundColor(v.getResources().getColor(R.color.rosado));
        TextView modo = (TextView) v.findViewById(R.id.modo);
        modo.setText("1");
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        CarritoActivity carritoActivity = (CarritoActivity) v.getContext();
        carritoActivity.modoBorrar(descripcion.getText().toString());
        return true;
    }

    @Override
    public void onClick(View v)
    {
        CarritoActivity carritoActivity = (CarritoActivity) v.getContext();
        CardView cardView = (CardView) v.findViewById(R.id.info_producto);
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);

        if (carritoActivity.isModoBorrar())
        {
            TextView modo = (TextView) v.findViewById(R.id.modo);
            if (modo.getText().toString().equals("0"))
            {
                cardView.setBackgroundColor(v.getResources().getColor(R.color.rosado));
                carritoActivity.agregarABasura(descripcion.getText().toString());
                modo.setText("1");
            }
            else
            {
                cardView.setBackgroundColor(v.getResources().getColor(R.color.white));
                carritoActivity.quitarDeBasura(descripcion.getText().toString());
                modo.setText("0");
            }
        }
        else
        {
            Articulo articulo = Articulo.obtenerInstancia(descripcion.getText().toString());
            Bundle bundle = new Bundle();
            bundle.putString("modo", "edicion");
            SeleccionarCantidadDialogFragment dialog = new SeleccionarCantidadDialogFragment(carritoActivity, articulo);
            dialog.setArguments(bundle);
            dialog.show(carritoActivity.getFragmentManager(), null);
        }
    }

    public class ListaProductosAdapter extends RecyclerView.ViewHolder
    {
        public ListaProductosAdapter(@NonNull View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.info_producto);
        }

        public void asignarDatos(ArticuloPxQ articulo)
        {
            ImageView imagenProd = (ImageView) cardView.findViewById(R.id.imagenProd);
            TextView descripcion = (TextView) cardView.findViewById(R.id.descripcion);
            TextView cantidadStock = (TextView) cardView.findViewById(R.id.cantidadStock);
            TextView subtotal = (TextView) cardView.findViewById(R.id.subtotal);

            //imagenProd.setImageResource();
            descripcion.setText(articulo.getArticulo().getDescripcion());
            cantidadStock.setText("" + articulo.getCantidad());
            subtotal.setText(formatearMonedaDolar(articulo.getArticulo().getPrecio() * articulo.getCantidad()));
        }
    }

}
