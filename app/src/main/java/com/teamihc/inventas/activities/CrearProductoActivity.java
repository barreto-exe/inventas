package com.teamihc.inventas.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.dialogs.ConfirmarEliminacionDialogFragment;
import com.teamihc.inventas.dialogs.SobreescribirDialogFragment;

import java.util.Date;

public class CrearProductoActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private String descripcion_original = "";
    private int cantidad_original = 0;
    private TextInputEditText descripcionProdView;
    private TextInputEditText costoView;
    private TextInputEditText precioView;
    private TextView precioBsView;
    private TextInputEditText codigoView;
    private TextInputEditText cantidadView;
    private Button guardar_btn;
    private Button editar_btn;
    private Button cancelar_btn;
    private Button borrar_btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        toolbar = findViewById(R.id.crear_bar);
        
        descripcionProdView = (TextInputEditText) findViewById(R.id.descripcionProd);
        costoView = (TextInputEditText) findViewById(R.id.costo);
        precioView = (TextInputEditText) findViewById(R.id.precio);
        precioBsView = (TextView) findViewById(R.id.precioBs);
        codigoView = (TextInputEditText) findViewById(R.id.codTxt);
        cantidadView = (TextInputEditText) findViewById(R.id.cantidad);
        guardar_btn = (Button) findViewById(R.id.guardar_btn);
        editar_btn = (Button) findViewById(R.id.editar_btn);
        cancelar_btn = (Button) findViewById(R.id.cancelar_btn);
        borrar_btn = (Button) findViewById(R.id.borrar_btn);

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        
        //si estamos en modo edicion
        if (getIntent().getExtras() != null)
        {
            llenarFormulario();
            
            guardar_btn.setVisibility(TextView.INVISIBLE);
            editar_btn.setVisibility(TextView.VISIBLE);
            cancelar_btn.setVisibility(TextView.INVISIBLE);
            borrar_btn.setVisibility(TextView.VISIBLE);
            bloquearCampos();
            
            //si estamos en modo creacion
        }
        else
        {
            guardar_btn.setVisibility(TextView.VISIBLE);
            editar_btn.setVisibility(TextView.INVISIBLE);
            cancelar_btn.setVisibility(TextView.VISIBLE);
            borrar_btn.setVisibility(TextView.INVISIBLE);
            desbloquearCampos();
        }
        
        descripcion_original = ((TextView) findViewById(R.id.descripcionProd)).getText().toString();
    
        agregarListeners();
    }
    
    private void agregarListeners()
    {
        //Actualizar equivalencia Dolar/Bs
        precioView.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            
            }
        
            @Override
            public void afterTextChanged(Editable s)
            {
                float precioBs = 0;
                try
                {
                    precioBs = Float.parseFloat(precioView.getText().toString());
                    precioBs *= Tasa.obtenerTasa().getMonto();
                }
                catch (Exception ex)
                {
                }
                precioBsView.setText(String.valueOf(precioBs));
            }
        });
    }
    
    private void llenarFormulario()
    {
        Articulo articulo = Articulo.obtenerInstancia(getIntent().getExtras().getString("descripcion"));
        
        descripcionProdView.setText(articulo.getDescripcion());
        costoView.setText(articulo.getCosto() + "");
        precioView.setText(articulo.getPrecio() + "");
        precioBsView.setText(articulo.getPrecioBs() + "");
        codigoView.setText(articulo.getCodigo());
        cantidadView.setText(articulo.getCantidad() + "");
        
        cantidad_original = articulo.getCantidad();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.crear_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_eliminar:{
                eliminarArticulo();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }*/
    
    public void actualizarArticulo(Articulo articulo, int cambio_stock)
    {
        articulo.actualizar();
        if (cambio_stock != 0)
        {
            articulo.agregarStock(cambio_stock, new Date());
        }
        finish();
    }
    
    public boolean validarDatos()
    {
        
        boolean todoBien = true;
        
        if (descripcionProdView.getText().toString().equals(""))
        {
            descripcionProdView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        if (costoView.getText().toString().equals(""))
        {
            costoView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        if (precioView.getText().toString().equals(""))
        {
            precioView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        if (cantidadView.getText().toString().equals("") ||
                Integer.parseInt(cantidadView.getText().toString()) < 0)
        {
            cantidadView.setBackgroundColor(getResources().getColor(R.color.rosado));
            todoBien = false;
        }
        
        return todoBien;
    }
    
    private void bloquearCampos()
    {
        descripcionProdView.setEnabled(false);
        costoView.setEnabled(false);
        precioView.setEnabled(false);
        codigoView.setEnabled(false);
        cantidadView.setEnabled(false);
    }
    
    private void desbloquearCampos()
    {
        descripcionProdView.setEnabled(true);
        costoView.setEnabled(true);
        precioView.setEnabled(true);
        codigoView.setEnabled(true);
        cantidadView.setEnabled(true);
    }
    
    public void permitirEdicion(View view)
    {
        guardar_btn.setVisibility(TextView.VISIBLE);
        editar_btn.setVisibility(TextView.INVISIBLE);
        cancelar_btn.setVisibility(TextView.VISIBLE);
        borrar_btn.setVisibility(TextView.INVISIBLE);
        desbloquearCampos();
    }
    
    public void bloquearEdicion(View view)
    {
        guardar_btn.setVisibility(TextView.INVISIBLE);
        editar_btn.setVisibility(TextView.VISIBLE);
        cancelar_btn.setVisibility(TextView.INVISIBLE);
        borrar_btn.setVisibility(TextView.VISIBLE);
        bloquearCampos();
    }
    
    public void salvarDatos(View view)
    {
        if (!validarDatos())
        {
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String descripcion = descripcionProdView.getText().toString();
        float costo = Float.parseFloat(costoView.getText().toString());
        float precio = Float.parseFloat(precioView.getText().toString());
        int cantidad = Integer.parseInt(cantidadView.getText().toString());
        String codigo = codigoView.getText().toString();
        
        Articulo articulo = new Articulo(descripcion, costo, precio, cantidad, codigo);
        int cambio_stock = cantidad - cantidad_original;
        
        //si se modifico la descripcion
        if (!descripcion_original.equals(articulo.getDescripcion()))
        {
            //la descripcion es unica??
            if (articulo.obtenerId() == -1)
            {
                //es un articulo nuevo?
                if (descripcion_original.equals(""))
                {
                    articulo.registrar();
                    Toast.makeText(getApplicationContext(), "Articulo registrado con exito", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    //Si se cambia la descripcion, eliminar articulo y registrar uno nuevo
                    Articulo articulo_eliminar = Articulo.obtenerInstancia(descripcion_original);
                    articulo_eliminar.eliminar();
                    articulo.registrar();
                    finish();
                }
                //Desea sobreescribir??
            }
            else
            {
                new SobreescribirDialogFragment(this, articulo, cambio_stock).show(getSupportFragmentManager(), null);
            }
        }
        else
        {
            //estamos en modo edicion, actualizar
            actualizarArticulo(articulo, cambio_stock);
        }
    }
    
    public void eliminarArticulo(View view)
    {
        Articulo articulo = Articulo.obtenerInstancia(descripcion_original);
        new ConfirmarEliminacionDialogFragment(this, articulo).show(getSupportFragmentManager(), null);
    }
    
    public void salir(View view)
    {
        finish();
    }
}