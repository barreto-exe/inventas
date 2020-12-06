package com.teamihc.inventas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamihc.inventas.backend.basedatos.DBMatriz;
import com.teamihc.inventas.backend.basedatos.DBOperacion;
import com.teamihc.inventas.fragments.EstadisticasFragment;
import com.teamihc.inventas.fragments.InventarioFragment;
import com.teamihc.inventas.R;
import com.teamihc.inventas.fragments.TasasFragment;
import com.teamihc.inventas.fragments.VentasFragment;

public class MainActivity extends AppCompatActivity
{
    // private View decorView ;
    private Toolbar toolbar;
    Dialog dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DBOperacion.verificarBaseDatos(getAssets());
        
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new VentasFragment()).commit();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        dialog = new Dialog(this);
        
    }
    
    private void ejemploBBDD()
    {
        //Cómo hacer un select
        String query = "SELECT * FROM v_tasas WHERE fecha = ? ORDER BY fecha DESC LIMIT 1";
        
        //Esto se hace siempre, cargar el query en la operación
        DBOperacion op = new DBOperacion(query);
        
        //Pasar los parámetros en el mismo orden de los signos de interrogación
        op.pasarParametro("2020-10-10");
        
        //Realizar la consulta y vaciar la pila de resultados en la variable resultado
        DBMatriz resultado = op.consultar();
    
        //Recorrer la pila de registros
        while (resultado.leer())
        {
            //Castear el valor de cada registro en la columnda cambio_dolar
            float cambioDolar = (float) resultado.getValor("monto");
            
            Log.println(Log.INFO, "Hola", String.valueOf(cambioDolar));
        }
    
        //********************************************************************************
        //Como hacer un insert o update
        query = "INSERT INTO v_tasas(monto, fecha, hora) VALUES (10.0, '2020-10-10', '14:00:00')";
        op = new DBOperacion(query);
        op.ejecutar();
    }
    
    
    //Llama a la pantalla de añadir venta
    public void addCarrito(View view)
    {
        Intent intent = new Intent(MainActivity.this, CarritoActivity.class);
        startActivity(intent);
    }
    
    //Llama a la pantalla de añadir producto al inventario
    public void addProducto(View view)
    {
        Intent intent = new Intent(MainActivity.this, CrearProductoActivity.class);
        startActivity(intent);
    }
    
    //Llama a la pantalla de cambiar la tasa de divisa
    public void cambiarTasa(View view)
    {
        dialog.setContentView(R.layout.view_cambiar_tasa);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText tasa = dialog.findViewById(R.id.tasa);
        Button aceptar = dialog.findViewById((R.id.aceptar_bttn));
        aceptar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!tasa.getText().toString().contains("-") && !tasa.getText().toString().isEmpty())
                {
                    dialog.dismiss();
                    //añadir el codigo para BBDD
                    Toast.makeText(MainActivity.this, "Tasa aceptada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    /*
    //Para el item de busqueda de productos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.top_bar,menu);
        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) { return true;  }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.buscar).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView =(SearchView) menu.findItem(R.id.buscar).getActionView();
        searchView.setQueryHint("Ingrese su búsqueda");
        return true;
    }*/
    
    //Es lo que hace que aparezcan las pantallas cuando son seleccionadas
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment fragment = null;
            switch (item.getItemId())
            {
                case R.id.nav_ventas:
                {
                    fragment = new VentasFragment();
                    break;
                }
                case R.id.nav_tasas:
                {
                    fragment = new TasasFragment();
                    break;
                }
                case R.id.nav_inventario:
                {
                    fragment = new InventarioFragment();
                    break;
                }
                case R.id.nav_estadisticas:
                {
                    fragment = new EstadisticasFragment();
                    break;
                }
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            return true;
        }
    };
    
}