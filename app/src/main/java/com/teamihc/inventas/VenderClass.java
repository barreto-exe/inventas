package com.teamihc.inventas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class VenderClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vender);

    }
    public void add_carrito(View view){
        Intent intent =new Intent(this, AggArticuloClass.class);
        startActivity(intent);
    }
}