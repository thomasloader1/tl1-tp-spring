package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import java.util.Date;

public class DatosAlquiler {
    private Integer cantidadHoras;
    private Bicicleta bicicleta;
    private Usuario usuario;
    private double precioxhora;

    private double precioAlquiler;

    public DatosAlquiler(){
    }

    public DatosAlquiler(Integer cantidadHoras, Bicicleta bicicleta,Usuario usuario ){
        this.cantidadHoras = cantidadHoras;
        this.bicicleta = bicicleta;
        this.usuario = usuario;
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

    public double getPrecioxhora() {
        return precioxhora;
    }

    public void setPrecioxhora(double precioxhora) {
        this.precioxhora = precioxhora;
    }

    public double getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(double precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }
}
