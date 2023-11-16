package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosBicicleta;

import java.util.List;

public interface ServicioAlquiler {
    void crearAlquiler(Alquiler alquiler) throws AlquilerValidacion;

    Alquiler comenzarAlquiler(DatosAlquiler datosAlquiler) throws AlquilerValidacion;

    void finalizarAlquiler(Long id);

    Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id);


    List<Alquiler> buscarAlquilerPorIdUsuario(DatosAlquiler datosAlquiler);

    List<Alquiler> obtenerAlquileresDelUsuario(DatosAlquiler datosAlquiler);

    List<Alquiler> obtenerTodosLosAlquileresDeUnaBicicleta(DatosAlquiler datosAlquiler);

    Alquiler obtenerAlquilerPorId(Long id);
}
