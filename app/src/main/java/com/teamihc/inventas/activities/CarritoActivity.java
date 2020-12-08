package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.FragmentTransaction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.fragments.ListaProductosVentaFragment;
import com.teamihc.inventas.fragments.VentasFragment;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;


public class CarritoActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Articulo> listaArticulos;
    private ListaProductosRecyclerViewAdapter adapter;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private ImageButton carrito_aceptar;
    private ImageButton carrito_cancelar;
    private ImageButton carrito_eliminar;
    private ImageButton carrito_retroceder;
    private FloatingActionButton floatingActionButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        toolbar = (Toolbar) findViewById(R.id.toolbar_carrito);
        setSupportActionBar(toolbar);
        
        recyclerView = (RecyclerView) findViewById(R.id.carrito_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        
        carrito_aceptar = (ImageButton) findViewById(R.id.carrito_aceptar);
        carrito_cancelar = (ImageButton) findViewById(R.id.carrito_cancelar);
        carrito_eliminar = (ImageButton) findViewById(R.id.carrito_eliminar);
        carrito_retroceder = (ImageButton) findViewById(R.id.carrito_retroceder);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.producto);
        
        listaArticulos = new ArrayList<Articulo>();
        adapter = new ListaProductosRecyclerViewAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);
        
        fragment = getFragmentManager().findFragmentById(R.id.fragment_lista_productos_venta);
        //transaction = getFragmentManager().beginTransaction();
        
        hideFragment();
    }
    
    public void hideFragment()
    {
        transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.hide(fragment);
        transaction.commit();
        
        carrito_aceptar.setVisibility(ImageButton.VISIBLE);
        carrito_cancelar.setVisibility(ImageButton.VISIBLE);
        carrito_retroceder.setVisibility(ImageButton.INVISIBLE);
        floatingActionButton.setVisibility(ImageButton.VISIBLE);
    }
    
    public void showFragment()
    {
        transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.show(fragment);
        transaction.commit();
        
        carrito_aceptar.setVisibility(ImageButton.INVISIBLE);
        carrito_cancelar.setVisibility(ImageButton.INVISIBLE);
        carrito_retroceder.setVisibility(ImageButton.VISIBLE);
        floatingActionButton.setVisibility(ImageButton.INVISIBLE);
    }
    
    public void cargarArticulo(String descripcion)
    {
        if (descripcion == null)
        {
            return;
        }
        Articulo articulo = Articulo.obtenerInstancia(descripcion);
        listaArticulos.add(0, articulo);
        adapter.notifyItemInserted(0);
    }
    
    public void add_carrito(View view)
    {
        showFragment();
    }
    
    public void retroceder(View view)
    {
        hideFragment();
    }
    
    public void cancelar(View view)
    {
        finish();
    }
    
    public void eliminar(View view)
    {
    
    }
    
    public void aceptar(View view)
    {
    
    }
}