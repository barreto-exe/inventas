package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.app.Fragment;
import android.widget.TextView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;

public class ListaProductosVenta extends AppCompatActivity {
    private Toolbar toolbar;
    private ListaProductosRecyclerViewAdapter.ListaProductosAdapter listaProductosAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Articulo> listaArticulos;
    private ListaProductosRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_venta);
        toolbar = (Toolbar) findViewById(R.id.toolbar_carrito);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.listaProductos_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        listaArticulos = new ArrayList<Articulo>();
        Articulo.cargarInventarioEnLista(listaArticulos);

        adapter = new ListaProductosRecyclerViewAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);
    }
}
