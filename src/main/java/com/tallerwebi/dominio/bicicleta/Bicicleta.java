package com.tallerwebi.dominio.bicicleta;

import com.tallerwebi.dominio.resenia.Resenia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Bicicleta {
    private Integer id;
    private Estado estado;
    @OneToMany(mappedBy = "bicicleta", cascade = CascadeType.ALL)
    private List<Resenia> resenias = new ArrayList<Resenia>();

    public Bicicleta(Integer id, Estado estado) {
        this.id = id;
        this.estado = estado;
    }

    public Bicicleta(Integer id, Estado estado, Resenia resenia) {
        this.id = id;
        this.estado = estado;
        this.resenias.add(resenia);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }
}
