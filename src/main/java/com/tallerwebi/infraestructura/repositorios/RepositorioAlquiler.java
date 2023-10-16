package com.tallerwebi.infraestructura.repositorios;


import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

import java.util.List;


public interface RepositorioAlquiler {
    Alquiler obtenerAlquilerporId(Long id);
    List<Alquiler> obtenerAlquilerPorUsuario(Usuario usuario);

    void crearAlquiler(Alquiler alquiler);

    void modificarAlquiler(Alquiler alquiler);

    List<Alquiler> obtenerAlquileresDeUnaBicicleta(Bicicleta bicicleta);
}
