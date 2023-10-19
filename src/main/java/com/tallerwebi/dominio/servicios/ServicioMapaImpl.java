package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.infraestructura.repositorios.RepositorioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioMapa")
@Transactional
public class ServicioMapaImpl implements ServicioMapa {
    private final RepositorioMapa repositorioMapa;

    @Autowired
    public ServicioMapaImpl(RepositorioMapa repositorioMapa) {
        this.repositorioMapa = repositorioMapa;
    }

    @Override
    public List<Usuario> obtenerPropietarios() {
        return repositorioMapa.obtenerPropietarios();
    }
}
