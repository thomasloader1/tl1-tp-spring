package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PropietarioTest {
    private Propietario propietario;

    @BeforeEach
    public void init() {
        propietario = mock(Propietario.class);
    }

    @Test
    public void queSePuedaObtenerElIdDelPropietario() {
        Long id = 1L;
        when(propietario.getId()).thenReturn(id);

        assertEquals(id, propietario.getId());
    }

    @Test
    public void queSePuedaEstablecerElIdDelPropietario() {
        Long id = 2L;
        propietario.setId(id);

        verify(propietario).setId(id);
    }

    @Test
    public void queSePuedaObtenerElNombreDelPropietario() {
        String nombre = "Bicicletería";
        when(propietario.getNombre()).thenReturn(nombre);

        assertEquals(nombre, propietario.getNombre());
    }

    @Test
    public void queSePuedaEstablecerElNombreDelPropietario() {
        String nombre = "Bicicletería";
        propietario.setNombre(nombre);

        verify(propietario).setNombre(nombre);
    }

    @Test
    public void queSePuedaObtenerLaDireccionDelPropietario() {
        String direccion = "Calle 123";
        when(propietario.getDireccion()).thenReturn(direccion);

        assertEquals(direccion, propietario.getDireccion());
    }

    @Test
    public void queSePuedaEstablecerLaDireccionDelPropietario() {
        String direccion = "Calle 123";
        propietario.setDireccion(direccion);

        verify(propietario).setDireccion(direccion);
    }
}
