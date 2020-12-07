package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;

public class ListaProductosVenta extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ListaProductosRecyclerViewAdapter.ListaProductosAdapter listaProductosAdapter;
    private ArrayList<Articulo> listaArticulos;
    ListaProductosRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_venta);
        recyclerView = findViewById(R.id.listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        toolbar = findViewById(R.id.seleccionToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        listaArticulos = new ArrayList<Articulo>();
        Articulo.cargarInventarioEnLista(listaArticulos);
        adapter = new ListaProductosRecyclerViewAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);


    }
}
