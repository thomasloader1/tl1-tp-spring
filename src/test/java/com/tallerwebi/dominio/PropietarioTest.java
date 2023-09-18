package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        String nombre = "Juan";
        when(propietario.getNombre()).thenReturn(nombre);

        assertEquals(nombre, propietario.getNombre());
    }

    @Test
    public void queSePuedaEstablecerElNombreDelPropietario() {
        String nombre = "Juan";
        propietario.setNombre(nombre);

        verify(propietario).setNombre(nombre);
    }

    @Test
    public void queSePuedaObtenerLaListaDeVehiculosDelPropietario() {
        Vehiculo vehiculo = mock(Vehiculo.class);
        when(propietario.getVehiculos()).thenReturn(List.of(vehiculo));

        assertEquals(1, propietario.getVehiculos().size());
    }

    @Test
    public void queSePuedaEstablecerLaListaDeVehiculosDelPropietario() {
        Vehiculo vehiculo = mock(Vehiculo.class);
        propietario.setVehiculos(List.of(vehiculo));

        verify(propietario).setVehiculos(List.of(vehiculo));
    }
}
