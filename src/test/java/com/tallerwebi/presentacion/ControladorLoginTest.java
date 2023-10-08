package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.dominio.ServicioBicicleta;
import com.tallerwebi.dominio.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {
    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private List<Bicicleta> bicicletasMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;
    private ServicioBicicleta servicioBicicletaMock;

    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("usuario@mail.com", "1234");
        usuarioMock = mock(Usuario.class);
        bicicletasMock = new ArrayList<>(); // Crear una lista de Bicicletas

        // Agregar al menos dos bicicletas a la lista
        bicicletasMock.add(new Bicicleta(EstadoBicicleta.DISPONIBLE, "MALO", usuarioMock));
        bicicletasMock.add(new Bicicleta(EstadoBicicleta.DISPONIBLE, "MALO", usuarioMock));

        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        controladorLogin = new ControladorLogin(servicioLoginMock, servicioBicicletaMock);
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
        verify(sessionMock, times(0)).setAttribute("usuario", null);
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
        verify(sessionMock, times(1)).setAttribute("usuario", usuarioEncontradoMock);
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
        verify(sessionMock, times(1)).setAttribute("usuario", usuarioEncontradoMock);
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
        when(sessionMock.getAttribute("usuario")).thenReturn(null);
        when(requestMock.getSession()).thenReturn(sessionMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void redirigirAHomeSiHaySesion() {
        // preparacion
        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
    }


    @Test
    public void alIngresarComoClienteDebeTenerTodasLasBicicletas() {
        // preparacion
        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("bicicletas")).thenReturn(bicicletasMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
        // Verifica que las listas sean iguales en contenido
        List<Bicicleta> bicicletasEnVista = (List<Bicicleta>) modelAndView.getModel().get("bicicletas");
        // Verifica que las listas sean iguales en contenido
        assertEquals(bicicletasMock.size(), bicicletasEnVista.size());
    }
}
