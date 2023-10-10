package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.infraestructura.repositorios.RepositorioUsuario;
import com.tallerwebi.presentacion.dto.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {
    private final RepositorioUsuario servicioLoginDao;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario servicioLoginDao) {
        this.servicioLoginDao = servicioLoginDao;
    }

    @Override
    public Usuario consultarUsuario(String email, String password) {
        return servicioLoginDao.buscarUsuario(email, password);
    }

    @Override
    public void registrar(DatosUsuario datosUsuario) throws UsuarioExistente, UsuarioSinRol {
        Usuario usuarioEncontrado = servicioLoginDao.buscarUsuarioPorEmail(datosUsuario.getEmail());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
        }
        if (datosUsuario.getRol() == null) {
            throw new UsuarioSinRol();
        }
        Usuario usuario = new Usuario(datosUsuario.getEmail(), datosUsuario.getPassword(), datosUsuario.getRol());
        servicioLoginDao.guardar(usuario);
    }
}