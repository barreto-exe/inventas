package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.adapters.HistorialTasaRVAdapter;

import java.util.ArrayList;

public class HistorialTasaActivity extends AppCompatActivity
{
    RecyclerView historico;
    Toolbar toolbar;
    HistorialTasaRVAdapter.HistorialTasaAdapter listaTasasAdapter;
    private ArrayList<Tasa> listaTasas;
    HistorialTasaRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_tasa);
        historico = findViewById(R.id.listaTasas);
        historico.setLayoutManager(new LinearLayoutManager(this));
        historico.getLayoutManager().setMeasurementCacheEnabled(false);
        toolbar = findViewById(R.id.historialToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.historico);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        listaTasas = new ArrayList<Tasa>();
        Tasa.cargarHistoricoEnLista(listaTasas);
        adapter = new HistorialTasaRVAdapter(listaTasas);
        historico.setAdapter(adapter);
    }
}