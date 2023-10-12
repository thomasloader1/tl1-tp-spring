package com.tallerwebi.dominio.entidad;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Alquiler {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
      //  private Double precioAlquiler;
        private Integer cantidadHoras;
        @Enumerated(EnumType.STRING)
        private EstadoAlquiler estadoAlquiler;
        @ManyToOne
        private Bicicleta bicicleta;
        @ManyToOne
        private Usuario usuario;

        public Alquiler() {
        }

        public Alquiler(Integer cantidadHoras, Bicicleta bicicleta, Usuario usuario) {
            this.cantidadHoras = cantidadHoras;
            this.bicicleta = bicicleta;
            this.usuario = usuario;
            estadoAlquiler = EstadoAlquiler.EN_CURSO;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

    public Integer getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(Integer cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public EstadoAlquiler getEstadoAlquiler() {
        return estadoAlquiler;
    }

    public void setEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
        this.estadoAlquiler = estadoAlquiler;
    }
}


