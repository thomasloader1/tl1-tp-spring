package com.tallerwebi.dominio.entidad;

import javax.persistence.*;

@Entity
public class Bicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EstadoBicicleta estadoBicicleta;
    private String descripcion;
    @ManyToOne
    private Usuario usuario;

    public Bicicleta() {
    }

    public Bicicleta(EstadoBicicleta estadoBicicleta, String descripcion, Usuario usuario) {
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
