package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

public class DatosResena {
    private Integer puntaje;
    private String comentario;
    private Bicicleta bicicleta;
    private Usuario usuario;

    public DatosResena() {
    }

    public DatosResena(Integer puntaje, String comentario, Bicicleta bicicleta, Usuario usuario) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.bicicleta = bicicleta;
        this.usuario = usuario;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
