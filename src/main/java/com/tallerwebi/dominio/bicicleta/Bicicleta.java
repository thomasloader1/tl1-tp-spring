package com.tallerwebi.dominio.bicicleta;

import javax.persistence.Id;

public class Bicicleta {
    private Integer id;
    private Estado estado;

    public Bicicleta(Integer id,  Estado estado) {
        this.id = id;
        this.estado = estado;
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
}
