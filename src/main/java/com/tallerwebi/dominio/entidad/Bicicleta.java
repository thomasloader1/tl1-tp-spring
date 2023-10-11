package com.tallerwebi.dominio.entidad;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EstadoBicicleta estadoBicicleta;
    private String descripcion;
    @OneToMany
    private List<Resenia> resenias = new ArrayList<Resenia>();
    @ManyToOne
    private Usuario usuario;

    @OneToOne
    private BicicletaStatus status;

    public Bicicleta(EstadoBicicleta estadoBicicleta, String descripcion, Usuario usuario) {
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Bicicleta(Long id, EstadoBicicleta estadoBicicleta, String descripcion, Resenia resenia, Usuario usuario) {
        this.id = id;
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
        this.resenias.add(resenia);
        this.usuario = usuario;
    }

    public Bicicleta() {

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

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
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

    public BicicletaStatus getStatus() {
        return status;
    }

    public void setStatus(BicicletaStatus status) {
        this.status = status;
    }
}
