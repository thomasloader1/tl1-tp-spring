package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

public class DatosBicicleta {
    private EstadoBicicleta estadoBicicleta;
    private String descripcion;
    private Usuario usuario;

    private String urlImagen;

    public DatosBicicleta() {
    }

    public DatosBicicleta(EstadoBicicleta estadoBicicleta, String descripcion, Usuario usuario) {
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public EstadoBicicleta getEstadoBicicleta() {
        return estadoBicicleta;
    }

    public void setEstadoBicicleta(EstadoBicicleta estadoBicicleta) {
        this.estadoBicicleta = estadoBicicleta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String    getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
