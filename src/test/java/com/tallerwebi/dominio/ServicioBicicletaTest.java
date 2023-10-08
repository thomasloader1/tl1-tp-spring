package com.tallerwebi.dominio;

import com.tallerwebi.dominio.servicios.ServicioBicicletaImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioBicicletaTest {
    private ServicioBicicletaImpl servicioBicicleta;
    private RepositorioBicicleta repositorioBicicletaMock;


    @BeforeEach
    public void init() {
        repositorioBicicletaMock = mock(RepositorioBicicleta.class);
        servicioBicicleta = new ServicioBicicletaImpl(repositorioBicicletaMock);
    }

//    @Test
//    public void queSePuedaActualizarElEstadoDeLaBicicleta() {
//        Bicicleta bicicleta1 = new Bicicleta(1, EstadoBicicleta.EN_USO);
//        servicioBicicleta.agregarBicicleta(bicicleta1);
//        servicioBicicleta.actualizarEstadoBicicleta(1, EstadoBicicleta.DISPONIBLE);
//
//        EstadoBicicleta estadoBicicletaActual = bicicleta1.getEstado();
//
//        Assertions.assertEquals(EstadoBicicleta.DISPONIBLE, estadoBicicletaActual);
//
//    }
//
//    @Test
//    public void queSePuedaVerificarLaDisponibilidad() throws BicicletaNoEncontrada, BicicletaNoDisponible {
//        Bicicleta bicicleta1 = new Bicicleta(1, EstadoBicicleta.DISPONIBLE);
//        servicioBicicleta.agregarBicicleta(bicicleta1);
//        assertTrue(servicioBicicleta.verificarDisponibilidad(1));
//    }

}
