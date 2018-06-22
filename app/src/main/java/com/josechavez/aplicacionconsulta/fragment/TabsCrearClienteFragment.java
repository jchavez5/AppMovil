package com.josechavez.aplicacionconsulta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josechavez.aplicacionconsulta.R;

/**
 * Created by Josechavez on 21/06/2018.
 */

public class TabsCrearClienteFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tabs_crear_cliente,container,false);
    }
}
