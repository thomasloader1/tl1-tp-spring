package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.presentacion.controladores.ControladorLogin;
import com.tallerwebi.presentacion.dto.DatosLogin;
import com.tallerwebi.presentacion.dto.DatosUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        servicioBicicletaMock = mock(ServicioBicicleta.class);

        // Agregar al menos dos bicicletas a la lista
        bicicletasMock.add(new Bicicleta(EstadoBicicleta.DISPONIBLE, "MALO", usuarioMock, "google.com.ar"));
        bicicletasMock.add(new Bicicleta(EstadoBicicleta.DISPONIBLE, "MALO", usuarioMock, "google.com.ar"));

        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        ServicioBicicleta servicioBicicletaMock = mock(ServicioBicicleta.class);
        controladorLogin = new ControladorLogin(servicioLoginMock, servicioBicicletaMock);
    }

    @Test
    public void loginConUsuarioYPasswordIncorrectosDeberiaLlevarALoginNuevamente() {
        // preparación
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

        // ejecución
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
        verify(sessionMock, times(0)).setAttribute("usuario", null);
    }

    @Test
    public void loginConUsuarioYPasswordCorrectosDeberiaLlevarAHomeComoCliente() {
        // preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioEncontradoMock.getRol()).thenReturn("Cliente");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(sessionMock, times(1)).setAttribute("usuario", usuarioEncontradoMock);
    }

    @Test
    public void loginConUsuarioYPasswordCorrectosDeberiaLlevarAHomeComoPropietario() {
        // preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioEncontradoMock.getRol()).thenReturn("Propietario");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(sessionMock, times(1)).setAttribute("usuario", usuarioEncontradoMock);
    }

    @Test
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente, UsuarioSinRol {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        verify(servicioLoginMock, times(1)).registrar(datosUsuarioMock);
    }

    @Test
    public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(datosUsuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
    }

    @Test
    public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        doThrow(RuntimeException.class).when(servicioLoginMock).registrar(datosUsuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
    }

    @Test
    public void errorEnRegistrarmeSiNoSeleccioneTipoDeUsuarioDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        doThrow(UsuarioSinRol.class).when(servicioLoginMock).registrar(datosUsuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Tenés que seleccionar tu tipo de usuario"));
    }

    @Test
    public void redirigirALoginSiNoHaySesion() {
        // preparación
        when(sessionMock.getAttribute("usuario")).thenReturn(null);
        when(requestMock.getSession()).thenReturn(sessionMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void redirigirAHomeSiHaySesion() {
        // preparación
        Bicicleta bicicleta = makeBici();
        List<Bicicleta> bicis = makeBiciList(bicicleta);

        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("bicicletas")).thenReturn(bicis);

        // ejecución
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
    }


    @Test
    public void alIngresarComoClienteDebeTenerTodasLasBicicletas() {
        // preparación
        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("bicicletas")).thenReturn(bicicletasMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
        // Verifica que las listas sean iguales en contenido
        List<Bicicleta> bicicletasEnVista = (List<Bicicleta>) modelAndView.getModel().get("bicicletas");
        // Verifica que las listas sean iguales en contenido
        assertEquals(bicicletasMock.size(), bicicletasEnVista.size());
    }


    @Test
    public void loginConUsuarioPropietarioPuedeVerClientesQueAlquilaronSusBicicletas() {
        // preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioEncontradoMock.getRol()).thenReturn("Propietario");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(sessionMock, times(1)).setAttribute("usuario", usuarioEncontradoMock);
    }

//    Bicicleta related
@Test
public void traerLosDatosDeTodasLasBicis() {
    Bicicleta bicicleta = makeBici();
    List<Bicicleta> bicis = makeBiciList(bicicleta);

    sessionMock.setAttribute("usuario", usuarioMock);
    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

    when(sessionMock.getAttribute("bicicletas")).thenReturn(bicis);

    ModelAndView mv = controladorLogin.irAHome(sessionMock);

    assertEquals("home", mv.getViewName());
    assertEquals(2, mv.getModel().size()); // rol & bicicletas
    assertTrue(mv.getModel().keySet().contains("bicicletas"));
    assertEquals(1, ((List<Bicicleta>)mv.getModel().get("bicicletas")).size());
    assertEquals(bicicleta, ((List<Bicicleta>) mv.getModel().get("bicicletas")).get(0));
    assertEquals(Condition.PERFECTO_ESTADO, ((List<Bicicleta>) mv.getModel().get("bicicletas")).get(0).getCondicion());
}

    @Test
    public void noExistenBicicletasEnviaMensajeDeError() {
        List<Bicicleta> bicis = new ArrayList<>();

        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        when(sessionMock.getAttribute("bicicletas")).thenReturn(bicis);

        ModelAndView mv = controladorLogin.irAHome(sessionMock);


        assertEquals("home", mv.getViewName());
        assertEquals(mv.getModel().size(), 2); //bicis & rol
        assertTrue(mv.getModel().keySet().contains("error"));
    }

    @Test
    public void errorDesconocidoEnviaAPaginaDeErrorConCodigo(){
        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        when(sessionMock.getAttribute("bicicletas")).thenReturn(null);

        ModelAndView mv = controladorLogin.irAHome(sessionMock);

        assertEquals("error", mv.getViewName());
        assertEquals(mv.getModel().size(), 1);
        assertTrue(mv.getModel().keySet().contains("code"));
        assertEquals(mv.getModel().get("code"), "520");
    }

    private List<Bicicleta> makeBiciList(Bicicleta bicicleta){
        List<Bicicleta> bicis = new ArrayList<>();
        bicis.add(bicicleta);
        return bicis;
    }

    private  Bicicleta makeBici(){
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setCondicion(Condition.PERFECTO_ESTADO);
        return bicicleta;
    }
}
