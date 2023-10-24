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

    List<Alquiler> buscarAlquilerPorIdUsuario(DatosAlquiler datosAlquiler);

    List<Alquiler> obtenerAlquileresDelUsuario(DatosAlquiler datosAlquiler);

    List<Alquiler> obtenerTodosLosAlquileresDeUnaBicicleta(DatosAlquiler datosAlquiler);

    double calcularPrecioAlquiler(DatosAlquiler datosAlquiler);
}
