package com.teamihc.inventas.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Articulo;

public class CrearProductoActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        toolbar=findViewById(R.id.home_bar);
        setSupportActionBar(toolbar);
        ActionBar actionbar= getSupportActionBar();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    public void salvarDatos(View view)
    {
        TextView descripcionProd = (TextView)findViewById(R.id.descripcionProd);
        TextView costo = (TextView)findViewById(R.id.costo);
        TextView precio = (TextView)findViewById(R.id.precio);
        TextView codigo = (TextView)findViewById(R.id.codigo);

        Articulo articulo = new Articulo(
                descripcionProd.getText().toString(),
                Float.parseFloat(costo.getText().toString()),
                Float.parseFloat(precio.getText().toString()),
                costo.getText().toString(),
                0,
                null
        );

        articulo.addToInventory();

        finish();
    }
}