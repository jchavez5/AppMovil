package com.josechavez.aplicacionconsulta.clases;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Josechavez on 20/06/2018.
 */

public class DB {
    private static String db1 = "Empresa";
    private static ArrayList<Clientes> clientes = new ArrayList();
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public static void guardar(Empresa empresa) {
        databaseReference.child(db1).child(empresa.getId()).setValue(empresa);
        }
    public static DatabaseReference findById(String id){
        return databaseReference.child(db1  ).child(id);
    }

    public static void setClientes(ArrayList<Clientes> clientes) {
        DB.clientes=clientes;
    }
}
