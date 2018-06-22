package com.josechavez.aplicacionconsulta.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.support.design.widget.TabLayout;

import com.josechavez.aplicacionconsulta.R;

/**
 * Created by Josechavez on 21/06/2018.
 */

public class ClienteFragment extends Fragment{
    private AppBarLayout appBarLayout;//gestionar la barra , prestañas
    private TabLayout tabs;//los tabs del layout
    private ViewPager viewPager;//vista de la prestaña selecionada

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View   view=inflater.inflate(R.layout.fragment_cliente,container,false);
        //agregar las prestañas y la barra solicitada

        View contenedor=(View)container.getParent();
        appBarLayout=(AppBarLayout)contenedor.findViewById(R.id.appbar);
        tabs=new TabLayout(getActivity());
        tabs.setTabTextColors(Color.parseColor("fffffff"),Color.parseColor("fffffff"));
        appBarLayout.addView(tabs);

        //agregar fragment dependiendo de la selecion
        viewPager=(ViewPager)view.findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appBarLayout.removeView(tabs);
    }
    public class ViewPagerAdapter extends FragmentStatePagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        String []  titulosTAbs={"CREAR","CONSULTAR"};

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:return new TabsCrearClienteFragment();
                case 1:return new TabsConsultarClienteFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosTAbs[position];
        }
    }


}
