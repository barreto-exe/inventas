package com.teamihc.inventas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

public class SobreescribirDialogFragment extends DialogFragment
{

    private CrearProductoActivity activity;
    private Articulo articulo;
    private int cambio_stock;
    
    public SobreescribirDialogFragment(CrearProductoActivity activity, Articulo articulo, int cambio_stock)
    {
        this.activity = activity;
        this.articulo = articulo;
        this.cambio_stock = cambio_stock;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ya existe un articulo con esta descripcion, ¿Desea sobreescribirlo?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        activity.actualizarArticulo(articulo, cambio_stock);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}