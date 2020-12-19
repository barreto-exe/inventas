package com.teamihc.inventas.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.adapters.HistorialVentaRVAdapter;
import com.teamihc.inventas.backend.entidades.Venta;

import java.util.ArrayList;

/**
 * @author Karen
 */
public class HistorialVentasActivity extends AppCompatActivity
{
    RecyclerView historico;
    Toolbar toolbar;
    HistorialVentaRVAdapter.HistorialVentaAdapter listaVentasAdapter;
    private ArrayList<Venta> listaVentas;
    HistorialVentaRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_ventas);
        historico = findViewById(R.id.listaVentas);
        historico.setLayoutManager(new LinearLayoutManager(this));
        historico.getLayoutManager().setMeasurementCacheEnabled(false);
        toolbar = findViewById(R.id.historialToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.historico);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        listaVentas = new ArrayList<Venta>();
        Venta.cargarVentasTotalesEnLista(listaVentas);
        adapter = new HistorialVentaRVAdapter(listaVentas);
        historico.setAdapter(adapter);
    }
}
