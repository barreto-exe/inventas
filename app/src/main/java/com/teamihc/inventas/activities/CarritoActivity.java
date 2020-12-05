package com.teamihc.inventas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teamihc.inventas.R;


public class CarritoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

    }
    public void add_carrito(View view){
        Intent intent =new Intent(this, AgregarArticuloActivity.class);
        startActivity(intent);
    }
}