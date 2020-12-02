package com.example.inventas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    // private View decorView ;
    private Toolbar toolbar;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new VentasPage()).commit();
        }
        BottomNavigationView bottomNavigationView= findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


    }


    //Llama a la pantalla de añadir venta
    public void add_Ventas(View view){
        Intent intent =new Intent(MainActivity.this, Add_venta.class);
        startActivity(intent);
    }

    //Llama a la pantalla de añadir producto al inventario
    public void add_Producto(View view){
        Intent intent =new Intent(MainActivity.this, Add_producto.class);
        startActivity(intent);
    }

    //Llama a la pantalla de cambiar la tasa de divisa
    public void cambiarTasa(View view){
        Intent intent =new Intent(MainActivity.this, AdministrarTasas.class);
        startActivity(intent);
    }

    //Para el item de busqueda de productos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.top_bar,menu);
        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) { return true;  }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.buscar).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView =(SearchView) menu.findItem(R.id.buscar).getActionView();
        searchView.setQueryHint("Ingrese su búsqueda");
        return true;
    }

    //Es lo que hace que aparezcan las pantallas cuando son seleccionadas
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment= null;
            switch (item.getItemId()){
                case R.id.nav_ventas:{
                    fragment= new VentasPage();
                    break;}
                case R.id.nav_tasas:{
                    fragment= new TasasPage();
                    break;}
                case R.id.nav_inventario: {
                    fragment= new InventarioPage();
                    break;}
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
            return  true;
        }

    };
}