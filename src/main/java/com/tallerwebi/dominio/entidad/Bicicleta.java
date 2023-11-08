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
    private String urlImagen;
    private double precioAlquilerPorHora;
    private double precioVenta;
    private int puntaje;
    @ManyToOne
    private Usuario usuario;
    @OneToOne
    private BicicletaStatus status;

    @Enumerated(EnumType.STRING)
    private Condition condicion;

    public Condition getCondicion() {
        return condicion;
    }

    public void setCondicion(Condition condicion) {
        this.condicion = condicion;
    }

    public Bicicleta(EstadoBicicleta estadoBicicleta, String descripcion, Usuario usuario, String urlImagen,double precioVenta, double precioAlquilerPorHora) {
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.urlImagen = urlImagen;
        this.precioVenta = precioVenta;
        this.precioAlquilerPorHora = (precioVenta * 1.2) / 100;
    }

    public Bicicleta(Long id, EstadoBicicleta estadoBicicleta, String descripcion, Usuario usuario) {
        this.id = id;
        this.estadoBicicleta = estadoBicicleta;
        this.descripcion = descripcion;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
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

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    public double getPrecioAlquilerPorHora() {
        return precioAlquilerPorHora;
    }
    public void setPrecioAlquilerPorHora(double precioAlquilerPorHora) {
        this.precioAlquilerPorHora = precioAlquilerPorHora;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
