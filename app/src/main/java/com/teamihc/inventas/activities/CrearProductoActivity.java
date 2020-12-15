package com.teamihc.inventas.activities;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.teamihc.inventas.backend.Herramientas.*;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.dialogs.ConfirmarEliminacionDialogFragment;
import com.teamihc.inventas.dialogs.ElegirProveedorDeImagenDialogFragment;
import com.teamihc.inventas.dialogs.SobreescribirDialogFragment;

import java.io.File;
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
    private boolean modoEdicion;
    private ImageView imagenProd;
    private FloatingActionButton fotoproducto_btn;
    private String imagen_path;
    private boolean foto_tomada;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        
        descripcionProdView = findViewById(R.id.descripcionProd);
        costoView = findViewById(R.id.costo);
        precioView = findViewById(R.id.precio);
        precioBsView = findViewById(R.id.precioBs);
        codigoView = findViewById(R.id.codTxt);
        cantidadView = findViewById(R.id.cantidad);
        imagenProd = findViewById(R.id.imagenProd);
        fotoproducto_btn = findViewById(R.id.fotoproducto_btn);
        
        toolbar = findViewById(R.id.crearArticuloToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.articulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        imagen_path = "";
        foto_tomada = false;
        
        //Crando carpeta temporal para fotos
        File temp = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp");
        temp.mkdir();
        
        modoEdicion = getIntent().getExtras() != null;
        if (modoEdicion)
        {
            llenarFormulario();
            bloquearCampos();
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
                precioBsView.setText(Herramientas.formatearMoneda(precioBs));
            }
        });
        
        //Salir de la ventana
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crear_bar, menu);
        
        if (!modoEdicion)
        {
            menu.setGroupVisible(R.id.group_edicion, false);
        }
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_eliminar:
            {
                eliminarArticulo();
                break;
            }
            case R.id.nav_editar:
            {
                permitirEdicion();
                break;
            }
            case R.id.nav_guardar:
            {
                salvarDatos();
                break;
            }
        }
        
        return true;
    }
    
    private void bloquearCampos()
    {
        descripcionProdView.setEnabled(false);
        costoView.setEnabled(false);
        precioView.setEnabled(false);
        codigoView.setEnabled(false);
        cantidadView.setEnabled(false);
        fotoproducto_btn.setVisibility(View.INVISIBLE);
    }
    
    private void permitirEdicion()
    {
        descripcionProdView.setEnabled(true);
        costoView.setEnabled(true);
        precioView.setEnabled(true);
        codigoView.setEnabled(true);
        cantidadView.setEnabled(true);
        fotoproducto_btn.setVisibility(View.VISIBLE);
    }
    
    private void llenarFormulario()
    {
        Articulo articulo = Articulo.obtenerInstancia(getIntent().getExtras().getString("descripcion"));
        
        descripcionProdView.setText(articulo.getDescripcion());
        costoView.setText(articulo.getCosto() + "");
        precioView.setText(articulo.getPrecio() + "");
        precioBsView.setText(Herramientas.formatearMoneda(articulo.getPrecioBs()) + "");
        codigoView.setText(articulo.getCodigo());
        cantidadView.setText(articulo.getCantidad() + "");
        int height = imagenProd.getDrawable().getIntrinsicHeight();
        int width = imagenProd.getDrawable().getIntrinsicWidth();
        if (!articulo.getImagen_path().equals(""))
        {
            imagenProd.setImageURI(getImageUriFromPath(articulo.getImagen_path()));
        }
        cantidad_original = articulo.getCantidad();
        imagen_path = articulo.getImagen_path();
    }
    
    public boolean validarDatos()
    {
        boolean todoBien = true;
        String error = "No puede estar vacío.";
        
        if (descripcionProdView.getText().toString().equals(""))
        {
            descripcionProdView.setError(error);
            todoBien = false;
        }
        if (costoView.getText().toString().equals(""))
        {
            costoView.setError(error);
            todoBien = false;
        }
        if (precioView.getText().toString().equals(""))
        {
            precioView.setError(error);
            todoBien = false;
        }
        if (cantidadView.getText().toString().equals("") ||
                Integer.parseInt(cantidadView.getText().toString()) < 0)
        {
            cantidadView.setError("Revise este campo.");
            todoBien = false;
        }
        
        return todoBien;
    }
    
    public void eliminarArticulo()
    {
        Articulo articulo = Articulo.obtenerInstancia(descripcion_original);
        new ConfirmarEliminacionDialogFragment(this, articulo).show(getSupportFragmentManager(), null);
    }
    
    public void salvarDatos()
    {
        if (!validarDatos())
        {
            return;
        }
        
        String descripcion = descripcionProdView.getText().toString();
        float costo = Float.parseFloat(costoView.getText().toString());
        float precio = Float.parseFloat(precioView.getText().toString());
        int cantidad = Integer.parseInt(cantidadView.getText().toString());
        String codigo = codigoView.getText().toString();
        
        //Si se tomo una foto guardar archivo temporal en la carpeta de fotos
        if (foto_tomada)
        {
            File temp = new File(imagen_path);
            File definitivo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), temp.getName());
            temp.renameTo(definitivo);
            imagen_path = definitivo.getAbsolutePath();
        }
        
        Articulo articulo = new Articulo(descripcion, costo, precio, cantidad, codigo, imagen_path);
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
                    if (articulo.registrar())
                    {
                        Toast.makeText(getApplicationContext(), "Articulo registrado con exito", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "ERROR al registrar articulo", Toast.LENGTH_SHORT).show();
                    }
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
            }
            else
            {
                String mensaje;
                //Desea sobreescribir producto activo??
                if (articulo.isActivo())
                {
                    mensaje = "Ya existe un producto con esta descripción, ¿sobreescribir?";
                }
                //Desea sobreescribir producto inactivo??
                else
                {
                    mensaje = "Existe un producto inactivo con esta descripción, ¿sobreescribir y actualizar?";
                }
                new SobreescribirDialogFragment(mensaje, this, articulo, cambio_stock).show(getSupportFragmentManager(), null);
            }
        }
        else
        {
            //estamos en modo edicion, actualizar
            actualizarArticulo(articulo, cambio_stock);
        }
    }
    
    public void actualizarArticulo(Articulo articulo, int cambio_stock)
    {
        articulo.actualizar();
        if (cambio_stock != 0)
        {
            articulo.agregarStock(cambio_stock, new Date());
        }
        finish();
    }
    
    //<-------------------------------Metodos para capturar una foto------------------------------->
    
    public void obtenerImagen(View view)
    {
        //Si ya se tomo una foto
        if (foto_tomada)
        {
            File imagen = new File(imagen_path);
            imagen.delete();
        }
        new ElegirProveedorDeImagenDialogFragment().show(getSupportFragmentManager(), null);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.Q)
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK)
        {
            foto_tomada = true;
            
            if (requestCode == PICTURE_FROM_GALLERY)
            {
                imagenProd.setImageURI(data.getData());
                imagen_path = guardarImgenDeGaleria(this, data.getData());
            }
            else
            {
                imagenProd.setImageURI(getImageUriFromPath(obtenerPathDeCamara()));
                imagen_path = obtenerPathDeCamara();
            }
        }
    }
    //<-------------------------------Metodos para capturar una foto------------------------------->
    
    @Override
    public void finish()
    {
        File temp = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp");
        File[] array = temp.listFiles();
        for (int i = 0; i < array.length; i++)
        {
            array[i].delete();
        }
        temp.delete();
        super.finish();
    }
}