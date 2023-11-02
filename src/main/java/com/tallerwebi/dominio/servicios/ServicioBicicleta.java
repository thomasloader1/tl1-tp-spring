package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.presentacion.dto.DatosBicicleta;

import java.util.List;

public interface ServicioBicicleta {
    void darDeAltaUnaBicicleta(DatosBicicleta datosBicicleta) throws BicicletaValidacion;
    void darDeBajaUnaBicicleta(Long id);
    Bicicleta obtenerBicicletaPorId(Long id) throws BicicletaNoEncontrada;
    List<Bicicleta> obtenerTodasLasBicicleta();
    List<Bicicleta> obtenerTodasLasBicicletasDisponibles();
    List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario);

    List<Bicicleta> obtenerBicicletasDisponiblesPorIdUsuario(Long id);
    Bicicleta actualizarEstadoBicicleta(Bicicleta bicicleta);
    boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontrada, BicicletaNoDisponible;
    List<Bicicleta> obtenerBicicletasDisponibles();
}
