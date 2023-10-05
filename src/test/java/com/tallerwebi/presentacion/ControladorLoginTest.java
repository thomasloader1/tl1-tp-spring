package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {
    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;

    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("usuario@mail.com", "1234");
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        controladorLogin = new ControladorLogin(servicioLoginMock);
    }

    @Test
    public void loginConUsuarioYPasswordIncorrectosDeberiaLlevarALoginNuevamente() {
        // preparacion
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
        verify(sessionMock, times(0)).setAttribute("ROL", "");
    }

    @Test
    public void loginConUsuarioYPasswordCorrectosDeberiaLlevarAHomeComoCliente() {
        // preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioEncontradoMock.getRol()).thenReturn("Cliente");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
    }

    @Test
    public void loginConUsuarioYPasswordCorrectosDeberiaLlevarAHomeComoPropietario() {
        // preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioEncontradoMock.getRol()).thenReturn("Propietario");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
    }

    @Test
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente, UsuarioSinRol {
        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        verify(servicioLoginMock, times(1)).registrar(usuarioMock);
    }

    @Test
    public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol {
        // preparacion
        doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(usuarioMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
    }

    @Test
    public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol {
        // preparacion
        doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuarioMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
    }

    @Test
    public void errorEnRegistrarmeSiNoSeleccioneTipoDeUsuarioDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol {
        // preparacion
        doThrow(UsuarioSinRol.class).when(servicioLoginMock).registrar(usuarioMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Tenés que seleccionar tu tipo de usuario"));
    }

    @Test
    public void redirigirALoginSiNoHaySesion() {
        // preparacion
        when(sessionMock.getAttribute("ROL")).thenReturn(null);
        when(requestMock.getSession()).thenReturn(sessionMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void redirigirAHomeSiHaySesion() {
        // preparacion
        when(sessionMock.getAttribute("ROL")).thenReturn("Cliente");
        when(requestMock.getSession()).thenReturn(sessionMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
    }
}
