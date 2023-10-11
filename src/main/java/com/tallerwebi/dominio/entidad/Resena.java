package com.tallerwebi.dominio.entidad;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer puntaje;
    private String comentario;
    private Date fecha;
    @ManyToOne
    private Bicicleta bicicleta;
    @ManyToOne
    private Usuario usuario;

    public Resena() {
    }

    public Resena(Integer puntaje, String comentario, Bicicleta bicicleta, Usuario usuario) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        fecha = new Date();
        this.bicicleta = bicicleta;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
