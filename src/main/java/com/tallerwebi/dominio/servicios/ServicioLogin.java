package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinDireccion;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.presentacion.dto.DatosUsuario;

public interface ServicioLogin {
    Usuario consultarUsuario(String email, String password);

    void registrar(DatosUsuario datosUsuario) throws UsuarioExistente, UsuarioSinRol, UsuarioSinDireccion;
}