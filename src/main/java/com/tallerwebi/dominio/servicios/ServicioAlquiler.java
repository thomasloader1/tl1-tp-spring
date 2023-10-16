package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;

import java.util.List;

public interface ServicioAlquiler {
    void crearAlquiler(DatosAlquiler datosAlquiler);

    List<Alquiler> obtenerBicicletasAlquiladas (Bicicleta bicicleta);

    Alquiler finalizarAlquiler(Long id);

    Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id);

    List<Alquiler> buscarAlquiler(DatosAlquiler datosAlquiler);

    List<Alquiler> buscarAlquilerPorIdUsuario(DatosAlquiler datosAlquiler);
}
