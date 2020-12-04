package com.example.inventas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AddVenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_venta);

    }
    public void add_carrito(View view){
        Intent intent =new Intent(this, Carrito.class);
        startActivity(intent);
    }
}