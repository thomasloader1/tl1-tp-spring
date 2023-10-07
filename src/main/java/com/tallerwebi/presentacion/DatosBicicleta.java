package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

public class DatosBicicleta {
    private EstadoBicicleta estadoBicicleta;
    private String descripcion;
    private Usuario usuario;

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
