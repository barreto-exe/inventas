package com.teamihc.inventas.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


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
        toolbar = findViewById(R.id.crearInclude);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.crear_bar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.guardar:{
                salvarDatos();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvarDatos()
    {
        TextView descripcionProdView = (TextView) findViewById(R.id.descripcionProd);
        TextView costoView = (TextView) findViewById(R.id.costo);
        TextView precioView = (TextView) findViewById(R.id.precio);
        TextView codigoView = (TextView) findViewById(R.id.codTxt);
        TextView cantidadView = (TextView) findViewById(R.id.cantidad);  //Esta es el nuevo componente que se añadió
    
        //Hay que validar los datos ingresados en los campos de texto!!!!!
        //Porque da error si se dejan en blanco o tienen mal formato
        String descripcion = descripcionProdView.getText().toString();
        float costo   = Float.parseFloat(costoView.getText().toString());
        float precio  = Float.parseFloat(precioView.getText().toString());
        int cantidad  = Integer.parseInt(cantidadView.getText().toString());
        String codigo = codigoView.getText().toString();
        
        Articulo articulo = new Articulo(descripcion, costo, precio, cantidad, codigo );
        
        //Verificar si el artículo existe
        if(articulo.obtenerId() != -1)
        {
            //Ya existe, se debería preguntar si quiere sobreescribir
            Toast.makeText(this, "Ya existe ese artículo, ¿desea sobreescribir?", Toast.LENGTH_SHORT).show();
        }
        else
        {
            articulo.registrar();
            finish();
        }
    }
}