package com.josechavez.aplicacionconsulta.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.josechavez.aplicacionconsulta.R;
import com.josechavez.aplicacionconsulta.clases.DB;
import com.josechavez.aplicacionconsulta.clases.Empresa;
import com.josechavez.aplicacionconsulta.clases.Utilidades;
import com.josechavez.aplicacionconsulta.fragment.ChatFragment;
import com.josechavez.aplicacionconsulta.fragment.ContenedorClienteFragment;
import com.josechavez.aplicacionconsulta.fragment.PagoFragment;
import com.josechavez.aplicacionconsulta.fragment.PolizaFragment;
import com.josechavez.aplicacionconsulta.fragment.TabConsulClientesFragment;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ContenedorClienteFragment.OnFragmentInteractionListener,
        TabConsulClientesFragment.OnFragmentInteractionListener{
    private ImageView Perfil;
    private TextView txtNombre,txtEmail;
    private Empresa empresa;
    private Intent i;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        Perfil = (ImageView) headerView.findViewById(R.id.imgProfile);
        txtNombre = (TextView) headerView.findViewById(R.id.txtNombre);
        txtEmail = (TextView) headerView.findViewById(R.id.txtEmail);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        String id = firebaseAuth.getCurrentUser().getUid();
        DB.findById(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    empresa=dataSnapshot.getValue(Empresa.class);
                    txtNombre.setText(empresa.getNombreEmpresa());
                    txtEmail.setText(empresa.getEmail());
                    if(empresa.getUri() != null){
                        String url=empresa.getUri();
                        Glide.with(Inicio.this).load(url).into(Perfil);
                    }
                   }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (Utilidades.cliente==1){
            actionBarSet2(getResources().getString(R.string.cliente),R.drawable.cliente);

        } if (Utilidades.pagos==1){
            actionBarSet2(getResources().getString(R.string.pagos),R.drawable.pagos);
        } if (Utilidades.poliza==1){

            actionBarSet2(getResources().getString(R.string.poliza),R.drawable.poliza);
        }
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new ChatFragment()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.chat));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.nav_salirx){
            i=new Intent(Inicio.this,pricipal.class);
            finish();
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    public void actionBarSet2(String titulo, int drawable){
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setLogo(drawable);
    }

public void actionBarSet(String titulo, int drawable,
                         int cliente,int pago,int poliza){
    getSupportActionBar().setTitle(titulo);
    getSupportActionBar().setLogo(drawable);
    Utilidades.cliente=cliente;
    Utilidades.pagos=pago;
    Utilidades.poliza=poliza;

}
    public void setCheckable(MenuItem[] item,boolean b){
        for (int i = 0; i < item.length; i++) {
            item[i].setCheckable(b);
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        if (id == R.id.nav_cliente) {
            // setCheckable(new MenuItem[]{},false);
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new ContenedorClienteFragment()).commit();
            actionBarSet(getResources().getString(R.string.cliente),R.drawable.cliente,1,0,0);

        } else if (id == R.id.nav_poliza) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new PolizaFragment()).commit();
            actionBarSet(getResources().getString(R.string.poliza),R.drawable.cliente,0,0,1);

        } else if (id == R.id.nav_pagos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new PagoFragment()).commit();
            actionBarSet(getResources().getString(R.string.pagos),R.drawable.pagos,0,1,0);

        }
        else if (id == R.id.nav_salir) {
            i=new Intent(Inicio.this,pricipal.class);
            finish();
            startActivity(i);
        }
        final NavigationView mainNavigationMenu_ = (NavigationView) findViewById(R.id.nav_view);
        mainNavigationMenu_.getMenu().findItem(id).setCheckable(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
