package com.teamihc.inventas.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

public class SeleccionarCantidadDialogFragment extends DialogFragment {

    private CarritoActivity activity;
    private Articulo articulo;

    public SeleccionarCantidadDialogFragment(){}

    @SuppressLint("ValidFragment")
    public SeleccionarCantidadDialogFragment(CarritoActivity activity, Articulo articulo) {
        this.activity = activity;
        this.articulo = articulo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(R.layout.view_seleccionar_cantidad, null);

        builder.setView(view);

        EditText cantidad = (EditText)view.findViewById(R.id.cantidad);
        TextView descripcion = (TextView)view.findViewById(R.id.articulo);
        TextView unidadesDispo = (TextView) view.findViewById(R.id.unidadesDispo);
        descripcion.setText(articulo.getDescripcion());
        unidadesDispo.setText(articulo.getCantidad()+"");


        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        int cantidad_int = Integer.parseInt(cantidad.getText().toString());

                        if (articulo.getCantidad() >= cantidad_int && cantidad_int != 0){

                            if (getArguments().getString("modo").equals("creacion")){

                                activity.cargarArticulo(articulo.getDescripcion(), cantidad_int);

                            }else{

                                activity.modificarCantidad(articulo.getDescripcion(), cantidad.getText().toString());
                            }

                            activity.hideFragment();

                        }else{
                            Toast.makeText(activity, "Cantidad invalida", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.hideFragment();
                    }
                });

        return builder.create();
    }
}
