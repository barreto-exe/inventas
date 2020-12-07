package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.teamihc.inventas.R;

public class HistorialTasaActivity extends AppCompatActivity
{
    RecyclerView historico;
    Toolbar toolbar;
    //Se debe declarar una lista o arreglo lo que sea, para poder vaciar el contenido de la BBDD en el layout info_tasa
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_tasa);
        historico = findViewById(R.id.historico);
        historico.setLayoutManager(new LinearLayoutManager(this));
        historico.getLayoutManager().setMeasurementCacheEnabled(false);
        toolbar = findViewById(R.id.historialToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}