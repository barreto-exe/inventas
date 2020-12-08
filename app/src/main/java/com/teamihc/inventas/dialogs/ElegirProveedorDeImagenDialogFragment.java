package com.teamihc.inventas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CrearProductoActivity;

public class ElegirProveedorDeImagenDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.seleccionar_imagen)
                .setItems(R.array.proveedores_imagen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CrearProductoActivity crearProductoActivity = (CrearProductoActivity) getActivity();
                        switch (which){
                            case 0: crearProductoActivity.imagenDesdeCamara(); break;
                            case 1: crearProductoActivity.imagenDesdeGaleria(); break;
                        }
                    }
                });

        return builder.create();
    }

}
