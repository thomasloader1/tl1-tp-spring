package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.presentacion.DatosUsuario;

public interface ServicioLogin {
    Usuario consultarUsuario(String email, String password);

    void registrar(DatosUsuario datosUsuario) throws UsuarioExistente, UsuarioSinRol;
}