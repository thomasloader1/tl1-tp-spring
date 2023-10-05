package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.servicio.ServicioBicicletaImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServicioBicicletaTest {

    private ServicioBicicletaImp servicioBicicleta;


    @BeforeEach
    public void init() {
        servicioBicicleta = new ServicioBicicletaImp();
    }

    @Test
    public void queSePuedaActualizarElEstadoDeLaBicicleta() {
        Bicicleta bicicleta1 = new Bicicleta(1, EstadoBicicleta.EN_USO);
        servicioBicicleta.agregarBicicleta(bicicleta1);
        servicioBicicleta.actualizarEstadoBicicleta(1, EstadoBicicleta.DISPONIBLE);

        EstadoBicicleta estadoBicicletaActual = bicicleta1.getEstado();

        Assertions.assertEquals(EstadoBicicleta.DISPONIBLE, estadoBicicletaActual);

    }

    @Test
    public void queSePuedaVerificarLaDisponibilidad() throws BicicletaNoEncontrada, BicicletaNoDisponible {
        Bicicleta bicicleta1 = new Bicicleta(1, EstadoBicicleta.DISPONIBLE);
        servicioBicicleta.agregarBicicleta(bicicleta1);
        assertTrue(servicioBicicleta.verificarDisponibilidad(1));
    }

}
