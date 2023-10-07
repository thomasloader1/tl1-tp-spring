package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

import java.util.List;

public interface RepositorioBicicleta {
    Bicicleta obtenerBicicletaPorId(Long id);

    void registrarBicicleta(Bicicleta bicicleta);

    void eliminarBicicleta(Bicicleta bicicleta);

    List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario);
}
