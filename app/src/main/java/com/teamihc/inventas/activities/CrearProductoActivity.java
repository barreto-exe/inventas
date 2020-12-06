package com.teamihc.inventas.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;

public class CrearProductoActivity extends AppCompatActivity
{//RECUERDA QUE ESTÁN LOS DOS BOTONES DE "aumentar" y "reducir" para poder actualizar el valor que aparece
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        toolbar = findViewById(R.id.home_bar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    public void salvarDatos(View view)
    {
        TextView descripcionProdView = (TextView) findViewById(R.id.descripcionProd);
        TextView costoView = (TextView) findViewById(R.id.costo);
        TextView precioView = (TextView) findViewById(R.id.precio);
        TextView codigoView = (TextView) findViewById(R.id.codigo);
        TextView cantidadView = (TextView) findViewById(R.id.cantidad);  //Esta es el nuevo componente que se añadió
    
        //Hay que validar los datos ingresados en los campos de texto!!!!!
        //Porque da error si se dejan en blanco o tienen mal formato
        String descripcion = descripcionProdView.getText().toString();
        float costo   = Float.parseFloat(costoView.getText().toString());
        float precio  = Float.parseFloat(precioView.getText().toString());
        int cantidad  = Integer.parseInt(cantidadView.getText().toString());
        String codigo = codigoView.getText().toString();
        
        new Articulo(descripcion, costo, precio, cantidad, codigo ).registrar();
        
        finish();
    }
}