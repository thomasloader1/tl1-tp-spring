package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinDireccion;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.servicios.ServicioResena;
import com.tallerwebi.presentacion.controladores.ControladorBicicleta;
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
import static org.mockito.Mockito.*;

public class ControladorLoginTest {
    private ControladorBicicleta controladorBicicleta;
    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private List<Bicicleta> bicicletasMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;
    private ServicioBicicleta servicioBicicletaMock;
    private ServicioResena servicioResena;

    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("usuario@mail.com", "1234");
        usuarioMock = mock(Usuario.class);
        bicicletasMock = new ArrayList<>(); // Crear una lista de Bicicletas
        // Agregar al menos dos bicicletas a la lista
        bicicletasMock.add(new Bicicleta(EstadoBicicleta.DISPONIBLE, "MALO", usuarioMock, "google.com.ar", 50000.0, 600.0));
        bicicletasMock.add(new Bicicleta(EstadoBicicleta.DISPONIBLE, "MALO", usuarioMock, "google.com.ar", 50000.0, 600.0));
        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        servicioResena = mock(ServicioResena.class);
        controladorBicicleta = new ControladorBicicleta(servicioBicicletaMock, servicioResena, sessionMock);
        controladorLogin = new ControladorLogin(servicioLoginMock, servicioBicicletaMock, servicioResena);
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
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente, UsuarioSinRol, UsuarioSinDireccion {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        when(datosUsuarioMock.getRol()).thenReturn("Cliente");

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        verify(servicioLoginMock, times(1)).registrar(datosUsuarioMock);
    }

    @Test
    public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol, UsuarioSinDireccion {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(datosUsuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
    }

    @Test
    public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol, UsuarioSinDireccion {
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
    public void errorEnRegistrarmeSiNoSeleccioneTipoDeUsuarioDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol, UsuarioSinDireccion {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        doThrow(UsuarioSinRol.class).when(servicioLoginMock).registrar(datosUsuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
    }

    @Test
    public void errorEnRegistrarmeSiNoIngreseDireccionDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, UsuarioSinRol, UsuarioSinDireccion {
        // preparación
        DatosUsuario datosUsuarioMock = mock(DatosUsuario.class);
        when(datosUsuarioMock.getRol()).thenReturn("Propietario");
        doThrow(UsuarioSinDireccion.class).when(servicioLoginMock).registrar(datosUsuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.registrarme(datosUsuarioMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Tenés que ingresar tu dirección"));
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
        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
    }


    @Test
    public void queSeMuestrenEnVistaClienteSoloLasBicicletasDisponibles() {
        // preparación
        sessionMock.setAttribute("usuario", usuarioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        when(servicioBicicletaMock.obtenerTodasLasBicicletasDisponibles()).thenReturn(bicicletasMock);
        // ejecución
        ModelAndView modelAndView = controladorLogin.irAHome(sessionMock);
        // validación

        List<Bicicleta> bicicletasEnVista = (List<Bicicleta>) modelAndView.getModel().get("bicicletas");
        // Verifica que las listas sean iguales en contenido
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
        assertEquals(bicicletasMock.size(), bicicletasEnVista.size());
    }

    @Test
    public void queEnElHomeDelPropietarioSeVeanLosStatsDeLosAlquileresYResenas() {
        // preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecucion
        ModelAndView modelAndView = controladorBicicleta.irAMisBicicletas();

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mis-bicicletas"));
//        verify(servicioBicicletaMock, times(1)).obtenerBicicletasEnUsoPorIdUsuario(usuarioMock.getId());
//        verify(servicioBicicletaMock, times(1)).obtenerBicicletasEnReparacionPorIdUsuario(usuarioMock.getId());
//        verify(servicioBicicletaMock, times(1)).obtenerBicicletasDisponiblesPorIdUsuario(usuarioMock.getId());
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
}