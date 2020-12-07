package com.teamihc.inventas.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.views.SobreescribirDialogFragment;

import java.util.Date;

public class CrearProductoActivity extends AppCompatActivity
{//RECUERDA QUE ESTÁN LOS DOS BOTONES DE "aumentar" y "reducir" para poder actualizar el valor que aparece
    private Toolbar toolbar;
    private String descripcion_original="";
    private int cantidad_original = 0;
    private TextView descripcionProdView;
    private TextView costoView;
    private  TextView precioView ;
    private TextView codigoView;
    private TextView cantidadView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        toolbar = findViewById(R.id.crearInclude);

        descripcionProdView = (TextView) findViewById(R.id.descripcionProd);
        costoView = (TextView) findViewById(R.id.costo);
        precioView = (TextView) findViewById(R.id.precio);
        codigoView = (TextView) findViewById(R.id.codTxt);
        cantidadView = (TextView) findViewById(R.id.cantidad);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null)
            llenarFormulario();

        descripcion_original = ((TextView)findViewById(R.id.descripcionProd)).getText().toString();
    }

    private void llenarFormulario() {
        Articulo articulo = Articulo.obtenerInstancia(getIntent().getExtras().getString("descripcion"));

        descripcionProdView.setText(articulo.getDescripcion());
        costoView.setText(articulo.getCosto() + "");
        precioView.setText(articulo.getPrecio() + "");
        codigoView.setText(articulo.getCodigo());
        cantidadView.setText(articulo.getCantidad()+"");

        cantidad_original = articulo.getCantidad();
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

    public void actualizarArticulo(Articulo articulo, int cambio_stock){
        articulo.actualizar();
        if (cambio_stock != 0){
            articulo.agregarStock(cambio_stock, new Date());
        }
        finish();
    }

    public boolean validarDatos(){

        boolean todoBien = true;

        if (descripcionProdView.getText().toString().equals("")){
            descripcionProdView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        if (costoView.getText().toString().equals("")){
            costoView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        if (precioView.getText().toString().equals("")){
            precioView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        if (cantidadView.getText().toString().equals("") ||
                Integer.parseInt(cantidadView.getText().toString()) < 0){
            cantidadView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }

        return todoBien;
    }

    public void salvarDatos()
    {
        if (!validarDatos()){
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }

        String descripcion = descripcionProdView.getText().toString();
        float costo   = Float.parseFloat(costoView.getText().toString());
        float precio  = Float.parseFloat(precioView.getText().toString());
        int cantidad  = Integer.parseInt(cantidadView.getText().toString());
        String codigo = codigoView.getText().toString();

        Articulo articulo = new Articulo(descripcion, costo, precio, cantidad, codigo );
        int cambio_stock = cantidad - cantidad_original;

        //si se modifico la descripcion
        if (!descripcion_original.equals(articulo.getDescripcion())){
            //la descripcion es unica??
            if (articulo.obtenerId() == -1){
                //es un articulo nuevo?
                if (descripcion_original.equals("")){
                    articulo.registrar();
                    finish();
                }else{
                    //Si se cambia la descripcion, eliminar articulo y registrar uno nuevo
                    Articulo articulo_eliminar = Articulo.obtenerInstancia(descripcion_original);
                    articulo_eliminar.eliminar();
                    articulo.registrar();
                    finish();
                }
            //Desea sobreescribir??
            }else{
                new SobreescribirDialogFragment(this, articulo, cambio_stock).show(getSupportFragmentManager(), null);
            }
        } else {
            //estamos en modo edicion, actualizar
            actualizarArticulo(articulo, cambio_stock);
        }
    }
}