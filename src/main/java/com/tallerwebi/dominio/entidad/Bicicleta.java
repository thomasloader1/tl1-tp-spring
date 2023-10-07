package com.tallerwebi.dominio.entidad;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class Bicicleta {
    private Integer id;
    private EstadoBicicleta estadoBicicleta;
    @OneToMany(mappedBy = "bicicleta", cascade = CascadeType.ALL)
    private List<Resenia> resenias = new ArrayList<Resenia>();

    public Bicicleta(Integer id, EstadoBicicleta estadoBicicleta) {
        this.id = id;
        this.estadoBicicleta = estadoBicicleta;
    }

    public Bicicleta(Integer id, EstadoBicicleta estadoBicicleta, Resenia resenia) {
        this.id = id;
        this.estadoBicicleta = estadoBicicleta;
        this.resenias.add(resenia);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoBicicleta getEstado() {
        return estadoBicicleta;
    }

    public void setEstado(EstadoBicicleta estadoBicicleta) {
        this.estadoBicicleta = estadoBicicleta;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }
}
