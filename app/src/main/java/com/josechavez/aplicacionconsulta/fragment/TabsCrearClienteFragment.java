package com.josechavez.aplicacionconsulta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.josechavez.aplicacionconsulta.R;
import com.josechavez.aplicacionconsulta.adapter.AdaptadorCliente;
import com.josechavez.aplicacionconsulta.clases.Clientes;
import com.josechavez.aplicacionconsulta.clases.DB;
import com.josechavez.aplicacionconsulta.clases.Empresa;
import com.josechavez.aplicacionconsulta.clases.Utilidades;
import com.josechavez.aplicacionconsulta.view.CrearEmpresa;

import java.nio.channels.CancelledKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Josechavez on 21/06/2018.
 */

public class TabsCrearClienteFragment extends Fragment{
    private Button btnAgregar;
    private EditText txtnombre,txtapellido,txtcedula,txttelefono;
    private String db="Empresa";
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth ;
    private ArrayList<Clientes> clientes=new ArrayList<>();
    private Boolean b;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs_crear_cliente,container,false);
        btnAgregar=(Button)view.findViewById(R.id.btnAgregar);


        txtnombre=(EditText)view.findViewById(R.id.txtNombreC);
        txtapellido=(EditText)view.findViewById(R.id.txtApellidoC);
        txtcedula=(EditText)view.findViewById(R.id.txtCedulaC);
        txttelefono=(EditText)view.findViewById(R.id.txtTelefonoC);

        firebaseAuth =FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validaciones
                final String nombre=txtnombre.getText().toString().trim()+" "+txtapellido.getText().toString().trim();
                final String cedula=txtcedula.getText().toString().trim();
                final String telefono=txttelefono.getText().toString().trim();
                final String id = firebaseAuth.getCurrentUser().getUid();
                //guardar();
                buscarCedula();


            }

        });
        return view;
    }
    public void  guardar(){
        final String id = firebaseAuth.getCurrentUser().getUid();
        final String nombre=txtnombre.getText().toString().trim()+" "+txtapellido.getText().toString().trim();
        final String cedula=txtcedula.getText().toString().trim();
        final String telefono=txttelefono.getText().toString().trim();
        DB.findById(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Clientes auxcliente=new Clientes(id,nombre,cedula,telefono);
                if (dataSnapshot.exists()) {
                    Empresa c = dataSnapshot.getValue(Empresa.class);
                    c.getClientes().add(auxcliente);
                    databaseReference.child(db).child(c.getId()).setValue(c);

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void  buscarCedula() {
        firebaseAuth = FirebaseAuth.getInstance();
        final String id = firebaseAuth.getCurrentUser().getUid();
        final String cedula = txtcedula.getText().toString().trim();
        final boolean[] valor = {false};

        DB.findById(id).child("clientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot snapshot = iterator.next();
                        Clientes c = snapshot.getValue(Clientes.class);
                        if (c.getCedula().equals(cedula)) {
                            valor[0] = true;
                        }
                    }
                    if (!valor[0]) {
                        Utilidades.ValidadcionDeBusqueda = 0;
                        guardar();
                        Toast.makeText(getActivity(), "Guardado Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Utilidades.ValidadcionDeBusqueda = 1;
                        Toast.makeText(getActivity(), "Problema ya existe un dato en la base de datos ", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    }
