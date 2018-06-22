package com.josechavez.aplicacionconsulta.clases;

import java.util.ArrayList;

/**
 * Created by Josechavez on 20/06/2018.
 */

public class Empresa {
    private String id;
    private String email;
    private String nombreEmpresa;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String nombreContacto;
    private String sexo;
    private String uri;
    private ArrayList<Polizas> polizas= new ArrayList<>();

    public Empresa() {
    }

    public Empresa(String id, String email, String nombreEmpresa, String direccion, String ciudad, String telefono, String nombreContacto, String sexo,String uri) {
        this.id = id;
        this.email = email;
        this.nombreEmpresa = nombreEmpresa;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.nombreContacto = nombreContacto;
        this.sexo = sexo;
        this.uri=uri;
        this.polizas= new ArrayList<Polizas>();

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public ArrayList<Polizas> getPolizas() {
        return polizas;
    }

    public void setPolizas(ArrayList<Polizas> polizas) {
        this.polizas = polizas;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", sexo='" + sexo + '\'' +
                ", uri='" + uri + '\'' +
                ", polizas=" + polizas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empresa)) return false;

        Empresa empresa = (Empresa) o;

        return getId() != null ? getId().equals(empresa.getId()) : empresa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
    public void guardar(){
        DB.guardar(this);
    }
}

