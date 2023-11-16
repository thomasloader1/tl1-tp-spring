package com.tallerwebi.presentacion.dto;

public class DatosPreferencia {

    private String id;
    public String nombreCliente;
    private String apellidoCliente;
    private String telefono;
    private String fechaPago;
    public String urlCheckout;

    public DatosPreferencia(String id, String nombreCliente, String apellidoCliente, String telefono, String fechaPago, String urlCheckout) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.telefono = telefono;
        this.fechaPago = fechaPago;
        this.urlCheckout = urlCheckout;
    }




}
