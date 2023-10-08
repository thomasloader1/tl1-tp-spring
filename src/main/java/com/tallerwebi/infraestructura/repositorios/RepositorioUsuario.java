package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Usuario;

public interface RepositorioUsuario {
    Usuario buscarUsuario(String email, String password);

    Usuario buscarUsuarioPorEmail(String email);

    void guardar(Usuario usuario);

    void modificar(Usuario usuario);
}