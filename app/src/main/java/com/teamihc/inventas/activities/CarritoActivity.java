package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.ArticuloPxQ;
import com.teamihc.inventas.backend.entidades.Carrito;
import com.teamihc.inventas.backend.entidades.Tasa;
import com.teamihc.inventas.backend.entidades.Venta;
import com.teamihc.inventas.adapters.listaproductos.CarritoRVAdapter;
import java.util.Date;
import java.util.LinkedList;


public class CarritoActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Carrito carrito;
    private CarritoRVAdapter adapter;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private ImageButton carrito_aceptar;
    private ImageButton carrito_cancelar;
    private ImageButton carrito_eliminar;
    private ImageButton carrito_retroceder;
    private FloatingActionButton floatingActionButton;
    private TextView contador;
    private ImageButton carrito_cancelar_eliminar;
    private boolean modoBorrar;
    private LinkedList<String> basura;
    private TextView carrito_total_dolares;
    private TextView carrito_total_bolivares;
    private TextView referencias_cargadas;
    private LinearLayout bienvenida;

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
    
        bienvenida = findViewById(R.id.carrito_bienvenida);
        
        carrito_aceptar = (ImageButton) findViewById(R.id.carrito_aceptar);
        carrito_cancelar = (ImageButton) findViewById(R.id.carrito_cancelar);
        carrito_eliminar = (ImageButton) findViewById(R.id.carrito_eliminar);
        carrito_retroceder = (ImageButton) findViewById(R.id.carrito_retroceder);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.producto);
        contador = (TextView) findViewById(R.id.contador);
        carrito_cancelar_eliminar = (ImageButton) findViewById(R.id.carrito_cancelar_eliminar);
        carrito_total_bolivares = (TextView) findViewById(R.id.carrito_total_bolivares);
        carrito_total_dolares = (TextView) findViewById(R.id.carrito_total_dolares);
        referencias_cargadas = (TextView) findViewById(R.id.referencias_cargadas);

        carrito = new Carrito();
        adapter = new CarritoRVAdapter(carrito);
        recyclerView.setAdapter(adapter);

        fragment = getFragmentManager().findFragmentById(R.id.fragment_lista_productos_venta);

        transaction = getFragmentManager().beginTransaction();
        transaction.hide(fragment);
        transaction.commit();

        carrito_aceptar.setVisibility(ImageButton.VISIBLE);
        carrito_cancelar.setVisibility(ImageButton.VISIBLE);
        carrito_retroceder.setVisibility(ImageButton.INVISIBLE);
        floatingActionButton.setVisibility(ImageButton.VISIBLE);

        modoBorrar = false;
        basura = new LinkedList<String>();
    
        calcularTotal();
    }

    public void cancelarAgregarCarrito(View view)
    {
        hideFragment();
    }
    
    public void hideFragment()
    {
        transaction = getFragmentManager().beginTransaction();
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
        carrito_cancelar.setVisibility(ImageButton.VISIBLE);
        carrito_retroceder.setVisibility(ImageButton.INVISIBLE);
        floatingActionButton.setVisibility(ImageButton.INVISIBLE);
    }

    public void modoBorrar(String descripcion)
    {
        toolbar.setBackgroundColor(getColor(R.color.rojo));
        toolbar.setTitle("");
        carrito_aceptar.setVisibility(View.INVISIBLE);
        carrito_cancelar.setVisibility(View.INVISIBLE);
        carrito_eliminar.setVisibility(View.VISIBLE);
        carrito_retroceder.setVisibility(View.INVISIBLE);
        floatingActionButton.setVisibility(View.INVISIBLE);
        contador.setVisibility(View.VISIBLE);
        carrito_cancelar_eliminar.setVisibility(View.VISIBLE);
        contador.setText("1");

        modoBorrar = true;
        agregarABasura(descripcion);
    }

    private void modoEditar()
    {
        toolbar.setBackgroundColor(getColor(R.color.colorPrimary));
        carrito_aceptar.setVisibility(View.VISIBLE);
        carrito_cancelar.setVisibility(View.VISIBLE);
        carrito_eliminar.setVisibility(View.INVISIBLE);
        carrito_retroceder.setVisibility(View.INVISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        contador.setVisibility(View.INVISIBLE);
        carrito_cancelar_eliminar.setVisibility(View.INVISIBLE);

        modoBorrar = false;
        actualizarLista();
    }

    public void agregarABasura(String descripcion)
    {
        basura.add(descripcion);
        contador.setText(basura.size() + "");
    }

    public void quitarDeBasura(String descripcion)
    {
        basura.remove(descripcion);
        contador.setText(basura.size() + "");
        if (basura.size() == 0)
        {
            modoEditar();
        }
    }

    public boolean isModoBorrar()
    {
        return modoBorrar;
    }

    public void cargarArticulo(String descripcion, int cantidad)
    {
        if (descripcion == null)
        {
            return;
        }
        for (ArticuloPxQ articulo : carrito.getCarrito())
        {
            if (articulo.getArticulo().getDescripcion().equals(descripcion))
            {
                Toast.makeText(this, "Este articulo ya fue elegido", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Articulo articulo = Articulo.obtenerInstancia(descripcion);
        carrito.agregarArticulo(articulo, cantidad);
        adapter.notifyItemInserted(carrito.getCarrito().size()-1);
        calcularTotal();
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

    public void cancelarEliminar(View view)
    {
        basura.clear();
        modoEditar();
        actualizarLista();
    }

    public void eliminar(View view)
    {
        // String s=basura.pop();
        for (int i = 0; i < basura.size(); i++)
        {
            Articulo articulo = Articulo.obtenerInstancia(basura.get(i));
            carrito.eliminarArticulo(articulo);
        }
        basura.clear();
        actualizarLista();
        modoEditar();
        calcularTotal();
    }

    private void actualizarLista()
    {
        adapter.notifyDataSetChanged();
        Carrito aux = new Carrito();

        for (int i = 0; i < carrito.getCarrito().size(); i++)
        {
            ArticuloPxQ articulo = carrito.getCarrito().get(i);
            aux.agregarArticulo(articulo.getArticulo(), articulo.getCantidad());
        }
        carrito.getCarrito().clear();
        adapter.notifyDataSetChanged();

        for (int i = 0; i < aux.getCarrito().size(); i++)
        {
            ArticuloPxQ articulo = aux.getCarrito().get(i);
            carrito.agregarArticulo(articulo.getArticulo(), articulo.getCantidad());
        }
        adapter = new CarritoRVAdapter(carrito);
        recyclerView.setAdapter(adapter);
    }
    
    public void aceptar(View view)
    {
        if (carrito.cantidadReferencias() == 0)
        {
            Toast.makeText(this, "No se ha registrado ningun articulo", Toast.LENGTH_SHORT).show();
            return;
        }

        Venta venta = new Venta(Tasa.obtenerTasa(), new Date(), carrito);
        venta.registrar();
        Toast.makeText(this, "Venta registrada con exito", Toast.LENGTH_SHORT).show();
        realFinish();
    }

    public void modificarCantidad(String descripcion, int cantidad)
    {
        for (ArticuloPxQ a : carrito.getCarrito())
        {
            if (a.getArticulo().getDescripcion().equals(descripcion))
            {
                a.setCantidad(cantidad);
            }
        }

        calcularTotal();
        actualizarLista();
    }

    public void calcularTotal()
    {
        referencias_cargadas.setText(carrito.getCarrito().size() + " referencias cargadas.");
        carrito_total_dolares.setText(Herramientas.formatearMonedaDolar(carrito.obtenerTotalDolares()));
        carrito_total_bolivares.setText(Herramientas.formatearMonedaBs(carrito.obtenerTotalBsS(Tasa.obtenerTasa())));
        
        colocarBienvenida();
    }
    
    private void colocarBienvenida()
    {
        if (carrito.cantidadReferencias() == 0)
        {
            bienvenida.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            bienvenida.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void finish(){
        if (fragment.isVisible()){
            hideFragment();
        }else{
            CarritoActivity carritoActivity = this;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Desea cancelar la compra?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carritoActivity.realFinish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }

    public void realFinish(){
        super.finish();
    }

}