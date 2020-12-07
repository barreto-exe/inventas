package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;


public class CarritoActivity extends AppCompatActivity
{
    Toolbar toolbar;
    ListaProductosRecyclerViewAdapter.ListaProductosAdapter listaProductosAdapter;
    RecyclerView recyclerView;
    private ArrayList<Articulo> listaArticulos;
    ListaProductosRecyclerViewAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        toolbar = (Toolbar) findViewById(R.id.toolbar_carrito);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.carrito_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        listaArticulos = new ArrayList<Articulo>();

        adapter = new ListaProductosRecyclerViewAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        TextView textView = (TextView)findViewById(R.id.carrito_descripcion_textView);
        if (textView.getText().toString().equals("")) return;
        Articulo articulo = Articulo.obtenerInstancia(textView.getText().toString());
        listaArticulos.add(0, articulo);
        adapter.notifyItemInserted(0);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.carrito_aceptar:
                Toast.makeText(this, "aceptar", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.carrito_cancelar:
                Toast.makeText(this, "cancelar", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }*/
    
    public void add_carrito(View view)
    {
        Intent intent = new Intent(this, ListaProductosVenta.class);
        startActivity(intent);
    }

    public void mensaje(View view){
        Toast.makeText(this, "Funciona", Toast.LENGTH_SHORT).show();
    }
}