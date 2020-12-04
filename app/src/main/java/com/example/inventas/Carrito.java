package com.example.inventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Carrito extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_carrito);

    }
   /* public void onClick(View view){
        Intent intent =new Intent(this,   Aqui va la clase de la nueva activity a la que te vas a dirigir ej MiClase.class    );
        startActivity(intent);
    }
    */
}