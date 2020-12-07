package com.teamihc.inventas.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.teamihc.inventas.R;


public class TasasFragment extends Fragment
{

    //aqui se vacia la info de la tasa actual y de la estadistica del cambio de tasas
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_tasas, container, false);
        
    }
}

