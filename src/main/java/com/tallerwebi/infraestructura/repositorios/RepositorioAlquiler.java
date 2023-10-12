package com.tallerwebi.infraestructura.repositorios;


import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;

public interface RepositorioAlquiler {
    Alquiler obtenerAlquilerporId(Long id);

    void finalizarAlquiler(Alquiler alquiler);
}
