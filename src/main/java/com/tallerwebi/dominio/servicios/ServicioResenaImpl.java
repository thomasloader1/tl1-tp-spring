package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.infraestructura.repositorios.RepositorioResena;
import com.tallerwebi.presentacion.dto.DatosResena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioResena")
@Transactional
public class ServicioResenaImpl implements ServicioResena {
    private final RepositorioResena repositorioResena;

    @Autowired
    public ServicioResenaImpl(RepositorioResena repositorioResena) {
        this.repositorioResena = repositorioResena;
    }

    @Override
    public void subirResena(DatosResena datosResena) throws ResenaValidacion, ResenaPuntajeValidacion {
        if (datosResena.getComentario().isEmpty() || datosResena.getPuntaje() == null) {
            throw new ResenaValidacion();
        }
        if (datosResena.getPuntaje() < 1 || datosResena.getPuntaje() > 5) {
            throw new ResenaPuntajeValidacion();
        }
        Resena resena = new Resena(datosResena.getPuntaje(), datosResena.getComentario(), datosResena.getBicicleta(), datosResena.getUsuario());
        repositorioResena.subirResena(resena);
    }

    @Override
    public List<Resena> obtenerResenasDeUnaBicicleta(Bicicleta bicicleta) {
        return repositorioResena.obtenerResenasDeUnaBicicleta(bicicleta);
    }
}
