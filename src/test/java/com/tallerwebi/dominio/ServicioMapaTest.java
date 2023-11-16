package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapaImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioMapaTest {
    private ServicioMapaImpl servicioMapa;
    private RepositorioMapa repositorioMapaMock;

    @BeforeEach
    public void init() {
        repositorioMapaMock = mock(RepositorioMapa.class);
        servicioMapa = new ServicioMapaImpl(repositorioMapaMock);
    }

    @Test
    public void queSePuedaObtenerUnaListaDePropietarios() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        when(repositorioMapaMock.obtenerPropietarios()).thenReturn(List.of(usuarioMock));

        // ejecución
        List<Usuario> propietarios = servicioMapa.obtenerPropietarios();

        // validación
        verify(repositorioMapaMock, times(1)).obtenerPropietarios();
        assertNotNull(propietarios);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeCincoPropietarios() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        when(repositorioMapaMock.obtenerPropietarios()).thenReturn(List.of(usuarioMock, usuarioMock, usuarioMock, usuarioMock, usuarioMock));

        // ejecución
        List<Usuario> propietarios = servicioMapa.obtenerPropietarios();

        // validación
        verify(repositorioMapaMock, times(1)).obtenerPropietarios();
        assertEquals(5, propietarios.size());
    }

    @Test
    public void queSePuedaObtenerUnaListaDeDistanciasALasUbicacionesDeUnaListaDePropietarios() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        when(repositorioMapaMock.obtenerPropietarios()).thenReturn(List.of(usuarioMock, usuarioMock, usuarioMock, usuarioMock, usuarioMock));

        // ejecución
        List<Usuario> propietarios = servicioMapa.obtenerPropietarios();
        List<String> distancias = servicioMapa.obtenerDistancias(propietarios);

        // validación
        verify(repositorioMapaMock, times(1)).obtenerPropietarios();
        assertEquals(5, distancias.size());
        assertTrue(distancias.get(0).contains("metros") || distancias.get(0).contains("km"));
        assertTrue(distancias.get(1).contains("metros") || distancias.get(1).contains("km"));
        assertTrue(distancias.get(2).contains("metros") || distancias.get(2).contains("km"));
        assertTrue(distancias.get(3).contains("metros") || distancias.get(3).contains("km"));
        assertTrue(distancias.get(4).contains("metros") || distancias.get(4).contains("km"));
    }

    @Test
    public void queSePuedaObtenerLaUbicacionActualDelUsuario() {
        // ejecución
        Coordenada coordenada = servicioMapa.obtenerUbicacionActual();

        // validación
        assertNotNull(coordenada);
        assertThat(coordenada.getLatitude(), instanceOf(double.class));
        assertThat(coordenada.getLongitude(), instanceOf(double.class));
    }
}
