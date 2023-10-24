package com.tallerwebi.presentacion.dto;

public class DatosUsuario {
    private String email;
    private String nombre;
    private String password;
    private String rol;
    private Double latitud;
    private Double longitud;

    public DatosUsuario() {
    }

    public DatosUsuario(String email, String nombre, String password, String rol, Double latitud, Double longitud) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}