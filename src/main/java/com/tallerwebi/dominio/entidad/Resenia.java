package com.tallerwebi.dominio.entidad;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comentario;
    private Date fecha;

    @JoinColumn(name = "bicicleta_id")
    private Integer bicicletaId;

    public Resenia() {
    }

    public Resenia(Integer id, String comentario, Date fecha, Integer bicicletaId) {
        this.id = id;
        this.comentario = comentario;
        this.fecha = fecha;
        this.bicicletaId = bicicletaId;
    }

    public Integer getBicicletaId() {
        return bicicletaId;
    }

    public void setBicicletaId(Integer bicicletaId) {
        this.bicicletaId = bicicletaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
