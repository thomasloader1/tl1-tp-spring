package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("ServicioAlquiler")
@Transactional
public class ServicioAlquilerImpl implements ServicioAlquiler {
    private final RepositorioAlquiler repositorioAlquiler;

    @Autowired
    public ServicioAlquilerImpl(RepositorioAlquiler repositorioAlquiler) {
        this.repositorioAlquiler = repositorioAlquiler;
    }

    @Override
    public void crearAlquiler(DatosAlquiler datosAlquiler) throws AlquilerValidacion {
        if (datosAlquiler.getCantidadHoras() < 1) {
            throw new AlquilerValidacion();
        }
        Alquiler alquiler = new Alquiler(datosAlquiler.getCantidadHoras(), datosAlquiler.getBicicleta(), datosAlquiler.getUsuario());
        repositorioAlquiler.crearAlquiler(alquiler);
    }

    @Override
    public void finalizarAlquiler(Long id) {
        Alquiler alquiler = repositorioAlquiler.obtenerAlquilerporId(id);
        alquiler.setEstadoAlquiler(EstadoAlquiler.FINALIZADO);
        repositorioAlquiler.eliminarAlquiler(alquiler);
    }

    @Override
    public Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id) {
        Alquiler alquiler = repositorioAlquiler.obtenerAlquilerporId(id);
        return alquiler.getBicicleta();
    }

    @Override
    public List<Alquiler> obtenerAlquileresDelUsuario(DatosAlquiler datosAlquiler) {
        return repositorioAlquiler.obtenerAlquilerPorUsuario(datosAlquiler.getUsuario());
    }

    @Override
    public List<Alquiler> obtenerTodosLosAlquileresDeUnaBicicleta(DatosAlquiler datosAlquiler) {
        return repositorioAlquiler.obtenerTodosLosAlquileresDeUnaBicicleta(datosAlquiler.getBicicleta());
    }

    @Override
    public List<Alquiler> buscarAlquilerPorIdUsuario(DatosAlquiler datosAlquiler) {

        return repositorioAlquiler.obtenerAlquilerPorUsuario(datosAlquiler.getUsuario());

    }
}
