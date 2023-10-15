package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service ("ServicioAlquiler")
@Transactional
public class ServicioAlquilerImpl implements ServicioAlquiler {
    private final RepositorioAlquiler repositorioAlquiler;

    @Autowired
    public ServicioAlquilerImpl(RepositorioAlquiler repositorioAlquiler) {
        this.repositorioAlquiler = repositorioAlquiler;
    }

    @Override
    public void crearAlquiler(DatosAlquiler datosAlquiler) {

    }

    @Override
    public List<Alquiler> obtenerBicicletasAlquiladas(Bicicleta bicicleta) {
        return null;
                //repositorioBicicletaAlquiler.obtenerBicicletasAlquiladas(bicicleta);
    }

    @Override
    public Alquiler finalizarAlquiler(Long id) {
        Alquiler alquiler = repositorioAlquiler.obtenerAlquilerporId(id);
        return repositorioAlquiler.finalizarAlquiler(alquiler);
    }

    @Override
    public Bicicleta obtenerBicicletaPorIdDeAlquiler(Long id) {
        Alquiler alquiler = repositorioAlquiler.obtenerAlquilerporId(id);
        return alquiler.getBicicleta();
    }

    @Override
    public List<Alquiler> buscarAlquiler(DatosAlquiler datosAlquiler) {
        return repositorioAlquiler.obtenerAlquilerDeBicicletas(datosAlquiler.getBicicleta());
    }
}
