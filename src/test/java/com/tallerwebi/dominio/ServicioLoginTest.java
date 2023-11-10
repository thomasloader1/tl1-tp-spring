package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinDireccion;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.dominio.servicios.ServicioLoginImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioUsuario;
import com.tallerwebi.presentacion.dto.DatosLogin;
import com.tallerwebi.presentacion.dto.DatosUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioLoginTest {
    private ServicioLoginImpl servicioLogin;
    private RepositorioUsuario repositorioUsuarioMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock);
    }

    @Test
    public void queSePuedaConsultarSiExisteUnUsuarioConEmailYPassword() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosLogin datosLoginMock = mock(DatosLogin.class);
        when(datosLoginMock.getEmail()).thenReturn("usuario@mail.com");
        when(datosLoginMock.getPassword()).thenReturn("1234");
        when(repositorioUsuarioMock.buscarUsuario(anyString(), anyString())).thenReturn(usuarioMock);

        // ejecución
        Usuario usuario = servicioLogin.consultarUsuario(datosLoginMock.getEmail(), datosLoginMock.getPassword());

        // validación
        verify(repositorioUsuarioMock, times(1)).buscarUsuario(anyString(), anyString());
        assertEquals(usuarioMock, usuario);
    }

    @Test
    public void queSePuedaRegistrarUnUsuario() throws UsuarioSinRol, UsuarioExistente, UsuarioSinDireccion {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        when(datosUsuarioMock.getEmail()).thenReturn("usuario@mail.com");
        when(datosUsuarioMock.getPassword()).thenReturn("1234");
        when(datosUsuarioMock.getRol()).thenReturn("Propietario");
        when(datosUsuarioMock.getDireccion()).thenReturn("Calle 123");
        when(repositorioUsuarioMock.buscarUsuarioPorEmail(datosUsuarioMock.getEmail())).thenReturn(null);

        // ejecución
        servicioLogin.registrar(datosUsuarioMock);

        // validación
        verify(repositorioUsuarioMock, times(1)).guardar(any(Usuario.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElUsuarioYaExisteYNoSePuedeRegistrarAlUsuario() {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        when(datosUsuarioMock.getEmail()).thenReturn("usuario@mail.com");
        when(datosUsuarioMock.getPassword()).thenReturn("1234");
        when(datosUsuarioMock.getRol()).thenReturn("Propietario");
        when(repositorioUsuarioMock.buscarUsuarioPorEmail(datosUsuarioMock.getEmail())).thenReturn(mock(Usuario.class));

        // ejecución y validación
        assertThrows(UsuarioExistente.class, () -> servicioLogin.registrar(datosUsuarioMock));
    }

    @Test
    public void queLanceUnaExcepcionSiElRolEsNuloYNoSePuedeRegistrarAlUsuario() {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        when(datosUsuarioMock.getEmail()).thenReturn("usuario@mail.com");
        when(datosUsuarioMock.getPassword()).thenReturn("1234");
        when(datosUsuarioMock.getRol()).thenReturn(null);
        when(repositorioUsuarioMock.buscarUsuarioPorEmail(datosUsuarioMock.getEmail())).thenReturn(null);

        // ejecución y validación
        assertThrows(UsuarioSinRol.class, () -> servicioLogin.registrar(datosUsuarioMock));
    }

    @Test
    public void queLanceUnaExcepcionSiLaDireccionEsNuloYNoSePuedeRegistrarAlUsuario() {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        when(datosUsuarioMock.getEmail()).thenReturn("usuario@mail.com");
        when(datosUsuarioMock.getPassword()).thenReturn("1234");
        when(datosUsuarioMock.getRol()).thenReturn("Propietario");
        when(datosUsuarioMock.getDireccion()).thenReturn(null);
        when(repositorioUsuarioMock.buscarUsuarioPorEmail(datosUsuarioMock.getEmail())).thenReturn(null);

        // ejecución y validación
        assertThrows(UsuarioSinDireccion.class, () -> servicioLogin.registrar(datosUsuarioMock));
    }
}