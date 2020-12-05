package com.teamihc.inventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class HistorialDeCambios extends AppCompatActivity {
    RecyclerView historico;
    //Se debe declarar una lista o arreglo lo que sea, para poder vaciar el contenido de la BBDD en el layout info_tasa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_de_cambios);
        historico=findViewById(R.id.historico);
        historico.setLayoutManager(new LinearLayoutManager(this));
    }
}