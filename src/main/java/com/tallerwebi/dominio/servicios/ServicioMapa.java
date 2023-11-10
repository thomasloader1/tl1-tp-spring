package com.tallerwebi.dominio.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;

import java.util.List;

public interface ServicioMapa {
    List<Usuario> obtenerPropietarios();

    List<String> obtenerDistancias(List<Usuario> propietarios);

    Coordenada obtenerUbicacionActual();

    List<String> obtenerTiempoDeLlegadaCaminando(List<Usuario> propietarios) throws JsonProcessingException;

    List<String> obtenerTiempoDeLlegadaEnBicicleta(List<Usuario> propietarios) throws JsonProcessingException;

    List<String> obtenerTiempoDeLlegadaEnAuto(List<Usuario> propietarios) throws JsonProcessingException;
}
