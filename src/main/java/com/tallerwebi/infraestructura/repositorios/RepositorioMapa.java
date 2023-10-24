package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Usuario;

import java.util.List;

public interface RepositorioMapa {
    public List<Usuario> obtenerPropietarios();
}
