package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquiler;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service("ServicioAlquiler")
@Transactional
public class ServicioAlquilerImpl implements ServicioAlquiler {
    private final RepositorioAlquiler repositorioAlquiler;
    private final RepositorioBicicleta repositorioBicicleta;

    @Autowired
    public ServicioAlquilerImpl(RepositorioAlquiler repositorioAlquiler, RepositorioBicicleta repositorioBicicleta) {
        this.repositorioAlquiler = repositorioAlquiler;
        this.repositorioBicicleta = repositorioBicicleta;
    }

    @Override
    public void crearAlquiler(Alquiler alquiler) {
        alquiler.setFechaAlquiler(LocalDateTime.now());
        alquiler.setEstadoAlquiler(EstadoBicicleta.EN_USO);
        repositorioBicicleta.updateEstado(alquiler.getBicicleta().getId(), EstadoBicicleta.EN_USO);
        repositorioAlquiler.crearAlquiler(alquiler);
    }

    @Override
    public Alquiler comenzarAlquiler(DatosAlquiler datosAlquiler) {
        Alquiler alquiler = new Alquiler(datosAlquiler.getCantidadHoras(), datosAlquiler.getBicicleta(), datosAlquiler.getUsuario());
        alquiler.setPrecioAlquiler(calcularPrecioAlquiler(datosAlquiler));
        return alquiler;
    }

    @Override
    public void finalizarAlquiler(Long id) {
        Alquiler alquiler = repositorioAlquiler.obtenerAlquilerporId(id);
        alquiler.setEstadoAlquiler(EstadoBicicleta.DISPONIBLE);
        repositorioBicicleta.updateEstado(alquiler.getBicicleta().getId(), EstadoBicicleta.DISPONIBLE);
    }

    @Override
    public Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id) {
        Alquiler alquiler = repositorioAlquiler.obtenerAlquilerporId(id);
        return alquiler.getBicicleta();
    }

    @Override
    public List<Alquiler> obtenerAlquileresDelUsuario(Usuario usuario) {
        return repositorioAlquiler.obtenerAlquilerPorUsuario(usuario);
    }

    @Override
    public List<Alquiler> obtenerTodosLosAlquileresDeUnaBicicleta(DatosAlquiler datosAlquiler) {
        return repositorioAlquiler.obtenerTodosLosAlquileresDeUnaBicicleta(datosAlquiler.getBicicleta());
    }

    private double calcularPrecioAlquiler(DatosAlquiler datosAlquiler) {
        double precioBasePorHora = datosAlquiler.getBicicleta().getPrecioAlquilerPorHora();
        int cantidadHoras = datosAlquiler.getCantidadHoras();
        Condition estadoBicicleta = datosAlquiler.getBicicleta().getCondicion();

        double valorEstado;

        switch (estadoBicicleta) {
            case PERFECTO_ESTADO:
                valorEstado = 1.0;
                break;
            case BUENO_ESTADO:
                valorEstado = 0.8;
                break;
            case MAL_ESTADO:
                valorEstado = 0.5;
                break;
            default:
                valorEstado = 1.0;
        }
        double precioFinal = precioBasePorHora * cantidadHoras * valorEstado;
        return precioFinal;
    }
}
