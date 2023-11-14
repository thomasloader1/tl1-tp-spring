package com.tallerwebi.dominio.servicios;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.presentacion.dto.DatosAlquiler;

public interface ServicioMercadoPago {

    public Preference crearPreferenciaPago(DatosAlquiler datosAlquiler) throws MPException, MPApiException;
}