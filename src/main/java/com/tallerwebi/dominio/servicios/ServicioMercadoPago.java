package com.tallerwebi.dominio.servicios;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosPreferencia;

public interface ServicioMercadoPago {

    DatosPreferencia crearPreferenciaPago(Alquiler alquiler) throws MPException, MPApiException;
}