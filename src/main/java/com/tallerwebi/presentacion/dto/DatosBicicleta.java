package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

public class DatosBicicleta {
    private EstadoBicicleta estadoBicicleta;
    private String descripcion;
    private Usuario usuario;

    private double precioAlquilerPorHora;
    private double precioVenta;
    private String urlImagen;

    public DatosBicicleta() {
    }

    public DatosBicicleta(EstadoBicicleta estadoBicicleta, String descripcion, Usuario usuario, double precioVenta) {
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.precioVenta = precioVenta;
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

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPrecioAlquilerPorHora() {
        return precioAlquilerPorHora;
    }

    public void setPrecioAlquilerPorHora(double precioAlquilerPorHora) {
        this.precioAlquilerPorHora = precioAlquilerPorHora;
    }
}
