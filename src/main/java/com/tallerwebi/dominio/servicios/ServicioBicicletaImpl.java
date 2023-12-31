package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.infraestructura.repositorios.RepositorioResena;
import com.tallerwebi.infraestructura.repositorios.RepositorioVehicleStatus;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service("servicioBicicleta")
@Transactional
public class ServicioBicicletaImpl implements ServicioBicicleta {

    private final RepositorioBicicleta repositorioBicicleta;
    private final RepositorioVehicleStatus repositorioVehicleStatus;
    private final ServicioVehicleStatus servicioVehicleStatus;
    private final RepositorioResena repositorioResena;

    @Autowired
    public ServicioBicicletaImpl(RepositorioBicicleta repositorioBicicleta, RepositorioVehicleStatus repositorioVehicleStatus, ServicioVehicleStatus servicioVehicleStatus, RepositorioResena repositorioResena) {
        this.repositorioBicicleta = repositorioBicicleta;
        this.repositorioVehicleStatus = repositorioVehicleStatus;
        this.servicioVehicleStatus = servicioVehicleStatus;
        this.repositorioResena = repositorioResena;
    }

    @Override
    public void darDeAltaUnaBicicleta(DatosBicicleta datosBicicleta) throws BicicletaValidacion {
        if (datosBicicleta.getEstadoBicicleta() == null || datosBicicleta.getDescripcion().isEmpty() || datosBicicleta.getPrecioVenta() <= 0) {
            throw new BicicletaValidacion();
        }
        Bicicleta bicicleta = new Bicicleta(datosBicicleta.getEstadoBicicleta(), datosBicicleta.getDescripcion(), datosBicicleta.getUsuario(), datosBicicleta.getUrlImagen(), datosBicicleta.getPrecioVenta(), datosBicicleta.getPrecioAlquilerPorHora());

        bicicleta.setPrecioVenta(datosBicicleta.getPrecioVenta());
        bicicleta.setCondicion(Condition.PERFECTO_ESTADO);
        bicicleta.setPuntaje(3);
        repositorioBicicleta.registrarBicicleta(bicicleta);
    }

    @Override
    public void darDeBajaUnaBicicleta(Long id) {
        Bicicleta bicicleta = repositorioBicicleta.obtenerBicicletaPorId(id);
        repositorioBicicleta.eliminarBicicleta(bicicleta);
    }

    @Override
    public List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario) {
        return repositorioBicicleta.obtenerBicicletasDelUsuario(usuario);
    }

    @Override
    public Bicicleta obtenerBicicletaPorId(Long id) throws BicicletaNoEncontrada {
        Bicicleta bicicleta = repositorioBicicleta.obtenerBicicletaPorId(id);
        if (bicicleta == null) {
            throw new BicicletaNoEncontrada("No se encontró la bicicleta.");
        }
        return bicicleta;
    }

    @Override
    public List<Bicicleta> obtenerBicicletasDisponiblesPorIdUsuario(Long id) {
        return repositorioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(id);
    }
    public List<Bicicleta> obtenerBicicletasEnReparacionPorIdUsuario(Long id){
        return repositorioBicicleta.obtenerBicicletasEnReparacionPorIdUsuario(id);
    };
    @Override
    public List<Bicicleta> obtenerTodasLasBicicleta() {
        return repositorioBicicleta.obtenerBicicletas();
    }

    @Override
    public List<Bicicleta> obtenerTodasLasBicicletasDisponibles() {
        return repositorioBicicleta.obtenerBicicletasDisponibles();
    }

    @Override
    public Bicicleta actualizarEstadoBicicleta(Bicicleta bicicleta) {
        if (bicicleta.getStatus() != null) {

            // Verifica si hay fallos en el estado del vehículo
            Set<String> fallos = servicioVehicleStatus.checkGeneralStatus(bicicleta.getStatus());

            if (!fallos.isEmpty()) {
                // Si hay fallos, cambia el estado a "requiere reparación"
                bicicleta.setEstadoBicicleta(EstadoBicicleta.REQUIERE_REPARACION);
                repositorioBicicleta.updateEstado(bicicleta.getId(), bicicleta.getEstadoBicicleta());
            }
        }
        return bicicleta;
    }

    @Override
    public boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontrada, BicicletaNoDisponible {
        Bicicleta bicicleta = repositorioBicicleta.obtenerBicicletaPorId((long) id);

        if (bicicleta != null) {
            if (bicicleta.getEstadoBicicleta() == EstadoBicicleta.DISPONIBLE) {
                return true;
            } else {
                throw new BicicletaNoDisponible("La bicicleta no está disponible.");
            }
        } else {
            throw new BicicletaNoEncontrada("No se encontró la bicicleta con el ID proporcionado.");
        }
    }
    @Override
    public List<Bicicleta> obtenerBicicletasEnUsoPorIdUsuario(Long id) {
        return repositorioBicicleta.obtenerBicicletasEnUsoPorIdUsuario(id);
    }

    public List<Bicicleta> obtenerBicicletasDisponibles() {
        List<Bicicleta> bicicleta = repositorioBicicleta.obtenerBicicletasDisponibles();
        return bicicleta;
    }

    @Override
    public void ordenarBicicletasDescendente(List<Bicicleta> bicicletas){
        Collections.sort(bicicletas, Collections.reverseOrder());
    };


}
