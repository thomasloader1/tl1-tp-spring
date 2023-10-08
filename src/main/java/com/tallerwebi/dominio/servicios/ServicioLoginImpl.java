package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.infraestructura.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario servicioLoginDao;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario servicioLoginDao) {
        this.servicioLoginDao = servicioLoginDao;
    }

    @Override
    public Usuario consultarUsuario(String email, String password) {
        return servicioLoginDao.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente, UsuarioSinRol {
        Usuario usuarioEncontrado = servicioLoginDao.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
        }
        if (usuario.getRol() == null) {
            throw new UsuarioSinRol();
        }
        servicioLoginDao.guardar(usuario);
    }

}

