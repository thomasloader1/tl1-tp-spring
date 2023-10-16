package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.servicios.ServicioAlquilerImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ServicioAlquilerTest {
    private ServicioAlquilerImpl servicioAlquiler;
    private RepositorioAlquiler repositorioAlquilerMock;

    @BeforeEach
    public void init() {
        repositorioAlquilerMock = mock(RepositorioAlquiler.class);
        servicioAlquiler = new ServicioAlquilerImpl(repositorioAlquilerMock);
    }

    @Test
    public void queSePuedaCrearUnAlquiler() throws AlquilerValidacion {
        // preparación
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(mock(Bicicleta.class));
        when(datosAlquilerMock.getUsuario()).thenReturn(mock(Usuario.class));
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);

        // ejecución
        servicioAlquiler.crearAlquiler(datosAlquilerMock);

        // validación
        verify(repositorioAlquilerMock, times(1)).crearAlquiler(any(Alquiler.class));
    }

    @Test
    public void queSePuedaFinalizarUnAlquiler() {
        // preparación
        Alquiler alquilerMock = mock(Alquiler.class);
        when(repositorioAlquilerMock.obtenerAlquilerporId(anyLong())).thenReturn(alquilerMock);
        when(alquilerMock.getEstadoAlquiler()).thenReturn(EstadoAlquiler.EN_CURSO);

        // ejecución
        servicioAlquiler.finalizarAlquiler(alquilerMock.getId());

        // validación
        when(alquilerMock.getEstadoAlquiler()).thenReturn(EstadoAlquiler.FINALIZADO);
        verify(repositorioAlquilerMock, times(1)).modificarAlquiler(alquilerMock);
        assertEquals(EstadoAlquiler.FINALIZADO, alquilerMock.getEstadoAlquiler());
    }

    @Test
    public void queSePuedaObtenerUnaBicicletaPorIdDeAlquiler() {
        // preparación
        Alquiler alquilerMock = mock(Alquiler.class);
        when(repositorioAlquilerMock.obtenerAlquilerporId(anyLong())).thenReturn(alquilerMock);
        when(alquilerMock.getBicicleta()).thenReturn(mock(Bicicleta.class));

        // ejecución
        Bicicleta bicicleta = servicioAlquiler.obtenerBicicletaPorIdDeAlquiler(alquilerMock.getId());

        // validación
        verify(repositorioAlquilerMock, times(1)).obtenerAlquilerporId(anyLong());
        assertEquals(alquilerMock.getBicicleta(), bicicleta);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeLosAlquileresDeUnaBicicleta() throws AlquilerValidacion {
        // preparación
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Usuario usuarioMock = mock(Usuario.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosAlquilerMock.getUsuario()).thenReturn(usuarioMock);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);

        // ejecución
        servicioAlquiler.crearAlquiler(datosAlquilerMock);
        when(repositorioAlquilerMock.obtenerAlquileresDeUnaBicicleta(bicicletaMock)).thenReturn(List.of(new Alquiler(1, bicicletaMock, usuarioMock)));
        List<Alquiler> alquileres = servicioAlquiler.obtenerAlquileresDeUnaBicicleta(datosAlquilerMock);

        // validación
        verify(repositorioAlquilerMock, times(1)).obtenerAlquileresDeUnaBicicleta(bicicletaMock);
        assertEquals(1, alquileres.size());
        assertEquals(datosAlquilerMock.getBicicleta(), alquileres.get(0).getBicicleta());
    }

    @Test
    public void queLanceUnaExcepcionSiLaCantidadDeHorasDeAlquilerEsMenorAUnoYNoSePuedeCrearElAlquiler() {
        // preparación
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Usuario usuarioMock = mock(Usuario.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosAlquilerMock.getUsuario()).thenReturn(usuarioMock);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(0);

        // ejecución y validación
        assertThrows(AlquilerValidacion.class, () -> servicioAlquiler.crearAlquiler(datosAlquilerMock));
        verify(repositorioAlquilerMock, times(0)).crearAlquiler(any(Alquiler.class));
    }
}
