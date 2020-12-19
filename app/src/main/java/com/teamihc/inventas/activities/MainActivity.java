package com.teamihc.inventas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.FragmentTransaction;

import android.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.*;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.basedatos.DBOperacion;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.fragments.EstadisticasFragment;
import com.teamihc.inventas.fragments.InventarioFragment;
import com.teamihc.inventas.R;
import com.teamihc.inventas.fragments.TasasFragment;
import com.teamihc.inventas.fragments.VentasFragment;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    // private View decorView ;
    private Toolbar toolbar;
    Dialog dialog;
    Menu menu;
    Date fechaConsultada;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DBOperacion.verificarBaseDatos(getAssets());
        Herramientas.inicializarFormatos();
        
        setContentView(R.layout.activity_main);
        
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    
        //Setear hoy como fecha consultada
        fechaConsultada = Calendar.getInstance().getTime();
        if (savedInstanceState == null)
        {
            setFechaConsultada(fechaConsultada);
        }
        
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        dialog = new Dialog(this);
    }
    
    public void setFechaConsultada(Date fechaConsultada)
    {
        this.fechaConsultada = fechaConsultada;
        
        VentasFragment v = new VentasFragment();
        v.setFechaConsultada(fechaConsultada);
        getFragmentManager().beginTransaction().replace(R.id.layout_principal, v).commit();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar, menu);
        this.menu = menu;
        menu.setGroupVisible(R.id.group_historial_tasas, false);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.top_calendario:
            {
                openConsultarDiaVentas(null);
                break;
            }
            case R.id.top_historial_tasas:
            {
                openHistorialTasas(null);
                break;
            }
            case R.id.top_historial_ventas:
            {
                openHistorialVentas(null);
                break;
            }
        }
        
        return true;
    }
    
    public void openHistorialTasas(View view)
    {
        Intent intent = new Intent(MainActivity.this, HistorialTasaActivity.class);
        startActivity(intent);
    }
    
    public void openHistorialVentas(View view)
    {
        Intent intent = new Intent(MainActivity.this, HistorialVentasActivity.class);
        startActivity(intent);
    }
    
    public void openCarrito(View view)
    {
        if(Articulo.cantidadArticulosRegistrados() < 1)
        {
            Toast.makeText(MainActivity.this, "No hay artículos registrados.", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Intent intent = new Intent(MainActivity.this, CarritoActivity.class);
        startActivity(intent);
    }
    
    public void openCrearProducto(View view)
    {
        Intent intent = new Intent(MainActivity.this, CrearProductoActivity.class);
        startActivity(intent);
    }

    public void openCambiarTasa(View view)
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
                    getFragmentManager().beginTransaction().replace(R.id.layout_principal, new TasasFragment()).commit();
                }
            }
        });
        dialog.show();
    }
    
    public void openConsultarDiaVentas(View view)
    {
        dialog.setContentView(R.layout.view_consultar_dia_ventas);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    
        CalendarView calendario = dialog.findViewById(R.id.consultar_dia_calendario);
        
        //Establece en el calendario la fecha consultada actualmente
        calendario.setDate(fechaConsultada.getTime());
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                fechaConsultada = new Date(year - 1900, month, dayOfMonth);
            }
        });
        calendario.setMaxDate(Calendar.getInstance().getTime().getTime());
        
        //Colocar listener de btn aceptar
        Button aceptar = dialog.findViewById((R.id.consultar_dia_aceptar_bttn));
        aceptar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                
                setFechaConsultada(fechaConsultada);
            }
        });
        dialog.show();
    }
    
    /**
     * Switch entre fragmets del bottom_bar en el main activity.
     */
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
                    menu.setGroupVisible(R.id.group_ventas, true);
                    menu.setGroupVisible(R.id.group_historial_tasas, false);
                    VentasFragment v = new VentasFragment();
                    v.setFechaConsultada(fechaConsultada);
                    fragment = v;
                    break;
                }
                case R.id.nav_tasas:
                {
                    menu.setGroupVisible(R.id.group_ventas, false);
                    menu.setGroupVisible(R.id.group_historial_tasas, true);
                    fragment = new TasasFragment();
                    break;
                }
                case R.id.nav_inventario:
                {
                    menu.setGroupVisible(R.id.group_ventas, false);
                    menu.setGroupVisible(R.id.group_historial_tasas, false);
                    fragment = new InventarioFragment();
                    break;
                }
                case R.id.nav_estadisticas:
                {
                    menu.setGroupVisible(R.id.group_ventas, false);
                    menu.setGroupVisible(R.id.group_historial_tasas, false);
                    fragment = new EstadisticasFragment();
                    break;
                }
            }
    
            try
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.replace(R.id.layout_principal, fragment);
                ft.commit();
            }
            catch (Exception exception)
            {
                String a = exception.getMessage();
            }
            
            return true;
        }
    };
    
}