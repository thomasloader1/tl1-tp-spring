package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;

import java.util.List;

public interface RepositorioResena {
    void subirResena(Resena resena);

    List<Resena> obtenerResenasDeUnaBicicleta(Bicicleta bicicleta);
    List<Resena> obtenerResenasDeUnaClientePorId(Long id);
}
