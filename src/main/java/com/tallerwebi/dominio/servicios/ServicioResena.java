package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.presentacion.dto.DatosResena;

import java.util.List;

public interface ServicioResena {
    void subirResena(DatosResena datosResena) throws ResenaValidacion, ResenaPuntajeValidacion;

    List<Resena> obtenerResenasDeUnaBicicleta(Bicicleta bicicleta);

    List<Resena> obtenerResenasDeUnaClientePorId(Long id);
}
