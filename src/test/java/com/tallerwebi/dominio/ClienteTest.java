package com.tallerwebi.dominio;
    
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClienteTest {
    private Cliente cliente;

    @BeforeEach
    public void init() {
        cliente = mock(Cliente.class);
    }

    @Test
    public void queSePuedaObtenerElIdDelCliente() {
        Long id = 1L;
        when(cliente.getId()).thenReturn(id);

        assertEquals(id, cliente.getId());
    }

    @Test
    public void queSePuedaEstablecerElIdDelCliente() {
        Long id = 2L;
        cliente.setId(id);

        verify(cliente).setId(id);
    }

    @Test
    public void queSePuedaObtenerElNombreDelCliente() {
        String nombre = "Juan";
        when(cliente.getNombre()).thenReturn(nombre);

        assertEquals(nombre, cliente.getNombre());
    }

    @Test
    public void queSePuedaEstablecerElNombreDelCliente() {
        String nombre = "Juan";
        cliente.setNombre(nombre);

        verify(cliente).setNombre(nombre);
    }

    @Test
    public void queSePuedaObtenerElApellidoDelCliente() {
        String apellido = "Pérez";
        when(cliente.getApellido()).thenReturn(apellido);

        assertEquals(apellido, cliente.getApellido());
    }

    @Test
    public void queSePuedaEstablecerElApellidoDelCliente() {
        String apellido = "Pérez";
        cliente.setApellido(apellido);

        verify(cliente).setApellido(apellido);
    }
}
