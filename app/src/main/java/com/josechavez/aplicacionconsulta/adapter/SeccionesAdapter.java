package com.josechavez.aplicacionconsulta.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josechavez on 22/06/2018.
 */

public class SeccionesAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> listaFragment=new ArrayList<>();
    private final List<String> listaTitulos=new ArrayList<>();
    public SeccionesAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment,String titulos){
        listaFragment.add(fragment);
        listaTitulos.add(titulos);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return listaTitulos.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragment.get(position);
    }

    @Override
    public int getCount() {
        return listaFragment.size();
    }
}
