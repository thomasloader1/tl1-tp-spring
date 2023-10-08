package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);

    void registrar(Usuario usuario) throws UsuarioExistente, UsuarioSinRol;

}
