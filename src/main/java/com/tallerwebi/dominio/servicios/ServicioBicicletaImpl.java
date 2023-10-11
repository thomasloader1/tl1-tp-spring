package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Resenia;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioBicicleta")
@Transactional
public class ServicioBicicletaImpl implements ServicioBicicleta {

    private final RepositorioBicicleta repositorioBicicleta;

    @Autowired
    public ServicioBicicletaImpl(RepositorioBicicleta repositorioBicicleta) {
        this.repositorioBicicleta = repositorioBicicleta;
    }

    @Override
    public void darDeAltaUnaBicicleta(DatosBicicleta datosBicicleta) throws BicicletaValidacion {
        if (datosBicicleta.getEstadoBicicleta() == null || datosBicicleta.getDescripcion().isEmpty()) {
            throw new BicicletaValidacion();
        }
        Bicicleta bicicleta = new Bicicleta(datosBicicleta.getEstadoBicicleta(), datosBicicleta.getDescripcion(), datosBicicleta.getUsuario());
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
    public Bicicleta obtenerBicicletaPorId(Long id) {
        return repositorioBicicleta.obtenerBicicletaPorId(id);
    }

    @Override
    public List<Bicicleta> obtenerTodasLasBicicleta() {
        return repositorioBicicleta.obtenerBicicletas();
    }

    @Override
    public void actualizarEstadoBicicleta(Long id, EstadoBicicleta estadoBicicleta) {
        Bicicleta bicicleta = this.obtenerBicicletaPorId(id);
        if (bicicleta != null) {
            repositorioBicicleta.updateEstado(bicicleta);
//            bicicleta.setEstadoBicicleta(estadoBicicleta);
        }
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
    public List<Resenia> verReseniasDeBicicleta(Integer id) {
        Bicicleta bicicleta = this.obtenerBicicletaPorId(id.longValue());
        return bicicleta.getResenias();
    }

    @Override
    public boolean agregarResenia(Resenia resenia) {
        // Obtén la bicicleta asociada a la reseña por su ID
        Bicicleta bicicleta = obtenerBicicletaPorId(resenia.getBicicletaId().longValue());

        if (bicicleta != null) {
            // Asocia la reseña a la bicicleta
            List<Resenia> resenias = bicicleta.getResenias();
            resenias.add(resenia);

            return true;
        }

        return false; // La bicicleta no existe
    }

}
