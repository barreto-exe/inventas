package com.example.inventas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
     //   getSupportActionBar().setTitle("InVentas");
      //  getSupportActionBar().setIcon(getDrawable(R.drawable.ic_logo));
      //  decorView=getWindow().getDecorView();
   /* decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            //detecta la visibilidad del UI
            if(visibility ==0)
                decorView.setSystemUiVisibility(hideSystemBar());
        }
    });
*/
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new VentasPage()).commit();
        }
            BottomNavigationView bottomNavigationView= findViewById(R.id.nav_bar);
            bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.top_bar,menu);
        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        };
        menu.findItem(R.id.buscar).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView =(SearchView) menu.findItem(R.id.buscar).getActionView();
        searchView.setQueryHint("Ingrese producto o transacción a buscar");
        return true;
    }

    /*  @Override
        public void onWindowFocusChanged(boolean d){
            super.onWindowFocusChanged(d);
            if(d){
                decorView.setSystemUiVisibility(hideSystemBar());

            }

        }

        private int hideSystemBar(){
            return View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
    */
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
