package com.teamihc.inventas.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.basedatos.DBOperacion;
import com.teamihc.inventas.backend.entidades.Articulo;

public class CrearProductoActivity extends AppCompatActivity
{//RECUERDA QUE ESTÁN LOS DOS BOTONES DE "aumentar" y "reducir" para poder actualizar el valor que aparece
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        toolbar=findViewById(R.id.home_bar);
        setSupportActionBar(toolbar);
        ActionBar actionbar= getSupportActionBar();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void salvarDatos(View view)
    {
        TextView descripcionProd = (TextView)findViewById(R.id.descripcionProd);
        TextView costo = (TextView)findViewById(R.id.costo);
        TextView precio = (TextView)findViewById(R.id.precio);
        TextView codigo = (TextView)findViewById(R.id.codigo);
        TextView cantidad = (TextView)findViewById(R.id.cantidad);  //Esta es el nuevo componente que se añadió
        Articulo articulo = new Articulo(
                descripcionProd.getText().toString(),
                Float.parseFloat(costo.getText().toString()),
                Float.parseFloat(precio.getText().toString()),
                0,
                codigo.getText().toString()
        );

        //articulo.registrar(articulo);

        //Articulo articulo = (Articulo) o;
        String query = "INSERT INTO v_articulos (descripcion, costo_unitario, precio_venta, " +
                "cantidad, codigo) VALUES(?,?,?,?,?)";
        DBOperacion op = new DBOperacion(query);
        op.pasarParametro(articulo.getDescripcion());
        op.pasarParametro(""+articulo.getCosto());
        op.pasarParametro(""+articulo.getPrecio());
        op.pasarParametro(""+articulo.getCantidad());
        op.pasarParametro(articulo.getCodigo());
        op.ejecutar();

        finish();
    }
}