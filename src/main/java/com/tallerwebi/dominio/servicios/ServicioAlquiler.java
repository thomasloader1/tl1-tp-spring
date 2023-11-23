package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.presentacion.dto.DatosAlquiler;

import java.util.List;

public interface ServicioAlquiler {
    void crearAlquiler(Alquiler alquiler) throws AlquilerValidacion;

    Alquiler comenzarAlquiler(DatosAlquiler datosAlquiler);

    void finalizarAlquiler(Long id);

    Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id);

    List<Alquiler> obtenerAlquileresDelUsuario(Usuario usuario);

    List<Alquiler> obtenerTodosLosAlquileresDeUnaBicicleta(DatosAlquiler datosAlquiler);
}
