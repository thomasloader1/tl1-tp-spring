package com.tallerwebi.dominio.servicio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Resenia;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioBicicletaImp implements ServicioBicicleta {

    private List<Bicicleta> bicicletas;

    public ServicioBicicletaImp() {

        this.bicicletas = new ArrayList<>();
    }

    @Override
    public Bicicleta obtenerBicicletaPorId(Integer id) {
        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta.getId() == id) {
                return bicicleta;
            }
        }
        return null;
    }

    @Override
    public void agregarBicicleta(Bicicleta bicicleta) {
        bicicletas.add(bicicleta);
    }

    @Override
    public void actualizarEstadoBicicleta(Integer id, EstadoBicicleta estadoBicicleta) {
        Bicicleta bicicleta = obtenerBicicletaPorId(id);
        if (bicicleta != null) {
            bicicleta.setEstado(estadoBicicleta);
        }
    }

    @Override
    public boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontrada, BicicletaNoDisponible {
        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta.getId() == id) {
                if (bicicleta.getEstado() == EstadoBicicleta.DISPONIBLE) {
                    return true;
                } else {
                    throw new BicicletaNoDisponible("La bicicleta no está disponible.");
                }
            }
        }
        throw new BicicletaNoEncontrada("No se encontró la bicicleta con el ID proporcionado.");
    }

    @Override
    public List<Resenia> verReseniasDeBicicleta(Integer id) {
        Bicicleta bicicleta = this.obtenerBicicletaPorId(id);
        return bicicleta.getResenias();
    }

    @Override
    public boolean agregarResenia(Resenia resenia) {
        // Obtén la bicicleta asociada a la reseña por su ID
        Bicicleta bicicleta = obtenerBicicletaPorId(resenia.getBicicletaId());

        if (bicicleta != null) {
            // Asocia la reseña a la bicicleta
            List<Resenia> resenias = bicicleta.getResenias();
            resenias.add(resenia);

            return true;
        }

        return false; // La bicicleta no existe
    }

}
