package com.tallerwebi.infraestructura.repositorios;


import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;

import java.util.List;


public interface RepositorioAlquiler {
    Alquiler obtenerAlquilerporId(Long id);
    void  crearAlquiler(Alquiler alquiler);
    Alquiler finalizarAlquiler(Alquiler alquiler);
    Alquiler modificarAlquiler(Alquiler alquiler);
    List<Alquiler> obtenerAlquilerDeBicicletas(Bicicleta bicicleta);

    List<Alquiler> obtenerBicicletasAlquiladas();
}
