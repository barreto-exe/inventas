package com.teamihc.inventas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.backend.entidades.Articulo;

public class ConfirmarEliminacionDialogFragment extends DialogFragment
{
    
    CrearProductoActivity activity;
    Articulo articulo;
    
    public ConfirmarEliminacionDialogFragment(CrearProductoActivity activity, Articulo articulo)
    {
        this.activity = activity;
        this.articulo = articulo;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Desea eliminar este articulo?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        articulo.eliminar();
                        activity.finish();
                        Toast.makeText(activity.getApplicationContext(), "Articulo eliminado con exito", Toast.LENGTH_SHORT).show();
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