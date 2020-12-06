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
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.backend.entidades.Venta;
import com.teamihc.inventas.fragments.EstadisticasFragment;
import com.teamihc.inventas.fragments.InventarioFragment;
import com.teamihc.inventas.R;
import com.teamihc.inventas.fragments.TasasFragment;
import com.teamihc.inventas.fragments.VentasFragment;

import java.util.Calendar;

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
    
    //Llama a la pantalla de añadir venta
    public void addCarrito(View view)
    {
        Articulo a1, a2, a3;
        a1 = new Articulo("leche de soya", 0.75f, 1, 12, null);
        a2 = new Articulo("almendras", 0.3f, 0.7f, 30, null);
        a3 = new Articulo("pan integral", 0.6f, 0.95f, 8, "12345");

        a1.registrar();
        a2.registrar();
        a3.registrar();

        Tasa t = new Tasa(120, Calendar.getInstance().getTime());
        t.registrar();

        Venta v = new Venta(t , Calendar.getInstance().getTime());
        v.getCarrito().agregarArticulo(a1, 2);
        v.getCarrito().agregarArticulo(a2, 18);
        v.getCarrito().agregarArticulo(a3, 3);
        v.getCarrito().eliminarArticulo(a1);
        v.getCarrito().eliminarArticulo(a2);
        v.registrar();
        Toast.makeText(MainActivity.this, "Venta registrada: " + v.getCarrito().obtenerTotal(), Toast.LENGTH_SHORT).show();
    }
    
    //Llama a la pantalla de añadir producto al inventario
    public void addProducto(View view)
    {
        Intent intent = new Intent(MainActivity.this, CrearProductoActivity.class);
        startActivity(intent);
    }
    
    //Llama a la pantalla de cambiar la tasa de divisa
    public void addTasa(View view)
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
                    Tasa t = new Tasa(Float.parseFloat(tasa.getText().toString()), Calendar.getInstance().getTime());
                    t.registrar();
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