package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapaImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
