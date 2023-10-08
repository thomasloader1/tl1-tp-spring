package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.infraestructura.RepositorioBicicleta;
import com.tallerwebi.presentacion.DatosBicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioBicicletaTest {
    private ServicioBicicletaImpl servicioBicicleta;
    private RepositorioBicicleta repositorioBicicletaMock;

    @BeforeEach
    public void init() {
        repositorioBicicletaMock = mock(RepositorioBicicleta.class);
        servicioBicicleta = new ServicioBicicletaImpl(repositorioBicicletaMock);
    }

    @Test
    public void queSePuedaDarDeAltaUnaBicicleta() throws BicicletaValidacion {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(datosBicicletaMock.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);
        when(datosBicicletaMock.getDescripcion()).thenReturn("Bicicleta de paseo");
        when(datosBicicletaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución
        servicioBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock);

        // validación
        verify(repositorioBicicletaMock, times(1)).registrarBicicleta(any(Bicicleta.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElEstadoDeBicicletaEsNuloYNoSePuedeDarDeAltaUnaBicicleta() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(datosBicicletaMock.getEstadoBicicleta()).thenReturn(null);
        when(datosBicicletaMock.getDescripcion()).thenReturn("Bicicleta de paseo");
        when(datosBicicletaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(BicicletaValidacion.class, () -> servicioBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock));
    }

    @Test
    public void queLanceUnaExcepcionSiLaDescripcionDeBicicletaEsVaciaxYNoSePuedeDarDeAltaUnaBicicleta() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(datosBicicletaMock.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);
        when(datosBicicletaMock.getDescripcion()).thenReturn("");
        when(datosBicicletaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(BicicletaValidacion.class, () -> servicioBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock));
    }

    @Test
    public void queSePuedaDarDeBajaUnaBicicleta() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletaPorId(anyLong())).thenReturn(bicicletaMock);

        // ejecución
        servicioBicicleta.darDeBajaUnaBicicleta(bicicletaMock.getId());

        // validación
        verify(repositorioBicicletaMock, times(1)).eliminarBicicleta(any(Bicicleta.class));
    }

    @Test
    public void queSePuedaObtenerUnaBicicletaPorId() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletaPorId(anyLong())).thenReturn(bicicletaMock);

        // ejecución
        Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(bicicletaMock.getId());

        // validación
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletaPorId(anyLong());
        assertEquals(bicicletaMock, bicicleta);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletas() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletas()).thenReturn(List.of(bicicletaMock));

        // ejecución
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerTodasLasBicicleta();

        // validación
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletas();
        assertEquals(1, bicicletas.size());
        assertEquals(bicicletaMock, bicicletas.get(0));
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletasDelUsuario() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletasDelUsuario(usuarioMock)).thenReturn(List.of(bicicletaMock));

        // ejecución
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerBicicletasDelUsuario(usuarioMock);

        // validación
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletasDelUsuario(usuarioMock);
        assertEquals(1, bicicletas.size());
        assertEquals(bicicletaMock, bicicletas.get(0));
    }
}
