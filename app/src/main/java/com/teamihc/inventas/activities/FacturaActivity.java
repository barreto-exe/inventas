package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Venta;
import com.teamihc.inventas.views.FacturaRVAdapter;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;

public class FacturaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FacturaRVAdapter.FacturaAdapter listaProductosAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Articulo> listaProductosVendidos;
    private FacturaRVAdapter adapter;
    Venta venta;
    TextView totalD, totalBsS,resumen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolb_factura);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Resumen de Venta");
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Falta codigo con respecto al llenado de la lista de ventas
        recyclerView = (RecyclerView) findViewById(R.id.factura);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        totalD=findViewById(R.id.totalD);
        totalBsS=findViewById(R.id.totalBsS);
        resumen=findViewById(R.id.itemsCompra);
        listaProductosVendidos = new ArrayList<Articulo>();
        //Aqui cargas la lista
        //falta poner el total tambien de la venta en dolares y bolivares y el numero de referencias (ver prototipo)
        //totalD.setText(" "+venta.obtener el monto de la venta);


        adapter = new FacturaRVAdapter(listaProductosVendidos);
        recyclerView.setAdapter(adapter);
    }
}