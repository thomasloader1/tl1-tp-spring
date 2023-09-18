package com.tallerwebi.domain;

import com.tallerwebi.dominio.bicicleta.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


public class ServicioBicicletaTest {

    private ServicioBicicletaImp servicioBicicleta;


    @BeforeEach
    public void init(){
        servicioBicicleta = new ServicioBicicletaImp();
    }

    @Test
    public void queSePuedaActualizarElEstadoDeLaBicicleta(){
        Bicicleta bicicleta1 = new Bicicleta(1, Estado.EN_USO);
        servicioBicicleta.agregarBicicleta(bicicleta1);
        servicioBicicleta.actualizarEstadoBicicleta(1,Estado.DISPONIBLE);

        Estado estadoActual = bicicleta1.getEstado();

        Assertions.assertEquals(Estado.DISPONIBLE, estadoActual);

    }

    @Test
    public void queSePuedaVerificarLaDisponibilidad() throws BicicletaNoEncontradaException, BicicletaNoDisponibleException {
        Bicicleta bicicleta1 = new Bicicleta(1, Estado.DISPONIBLE);
        servicioBicicleta.agregarBicicleta(bicicleta1);
        assertTrue(servicioBicicleta.verificarDisponibilidad(1));
    }

}
