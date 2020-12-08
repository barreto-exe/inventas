package com.teamihc.inventas.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.views.ListaProductosRecyclerViewAdapter;

import java.util.ArrayList;

public class ListaProductosVentaFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Articulo> listaArticulos;
    private ListaProductosRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lista_productos_venta, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_carrito);
        //setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.listaProductos_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        listaArticulos = new ArrayList<Articulo>();
        Articulo.cargarInventarioEnLista(listaArticulos);

        adapter = new ListaProductosRecyclerViewAdapter(listaArticulos);
        recyclerView.setAdapter(adapter);

        return view;
    }
}