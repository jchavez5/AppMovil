package com.josechavez.aplicacionconsulta.clases;

import java.util.ArrayList;

/**
 * Created by Josechavez on 26/06/2018.
 */

public class Clientes {
    private String id;
    private String nombre;
    private String cedula;
    private String telefono;
    private ArrayList<Polizas> polizas= new ArrayList<>();

    public Clientes() {
    }

    public Clientes(String cedula) {
        this.cedula = cedula;
    }

    public Clientes( String id,String nombre, String cedula, String telefono) {
        this.id=id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.polizas = new ArrayList<Polizas>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public ArrayList<Polizas> getPolizas() {
        return polizas;
    }

    public void setPolizas(ArrayList<Polizas> polizas) {
        this.polizas = polizas;
    }

    @Override
    public String toString() {
        return "Clientes{" +
                ", nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", polizas=" + polizas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clientes)) return false;

        Clientes clientes = (Clientes) o;

        return getCedula() != null ? getCedula().equals(clientes.getCedula()) : clientes.getCedula() == null;
    }

    @Override
    public int hashCode() {
        return getCedula() != null ? getCedula().hashCode() : 0;
    }
}
