package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.presentacion.dto.DatosAlquiler;

import java.util.List;

public interface ServicioAlquiler {
    void crearAlquiler(DatosAlquiler datosAlquiler) throws AlquilerValidacion;

    void finalizarAlquiler(Long id);

    Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id);

    List<Alquiler> obtenerAlquileresDeUnaBicicleta(DatosAlquiler datosAlquiler);
}
