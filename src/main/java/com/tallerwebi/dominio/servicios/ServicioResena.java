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
    List<Resena> obtenerResenasDeUnaClientePorIdPuntajeBueno(Long id);
    List<Resena> obtenerResenasDeUnaClientePorIdPuntajeRegular(Long id);
    List<Resena> obtenerResenasDeUnaClientePorIdPuntajeMalo(Long id);

    int calcularPuntaje(Bicicleta bicicleta);
}
