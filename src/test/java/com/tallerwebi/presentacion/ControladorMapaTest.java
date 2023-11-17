package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import com.tallerwebi.presentacion.controladores.ControladorMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorMapaTest {
    private ControladorMapa controladorMapa;
    private HttpSession sessionMock;
    private ServicioMapa servicioMapaMock;
    private Usuario usuarioMock;
    private Alquiler alquilerMock;

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        servicioMapaMock = mock(ServicioMapa.class);
        controladorMapa = new ControladorMapa(servicioMapaMock);
        usuarioMock = mock(Usuario.class);
        alquilerMock = mock(Alquiler.class);
        sessionMock.setAttribute("usuario", usuarioMock);
        sessionMock.setAttribute("alquiler", alquilerMock);
    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverLaVistaDeMapa() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mapa"));
    }

    @Test
    public void irAMapaConAlquilerEnCursoDebeDevolverLaVistaDeMapaAlquiler() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, alquilerMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mapa-alquiler"));
    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverLaVistaDeMapaConLosPropietarios() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecucion

        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validacion
        assertNotNull(modelAndView.getModel().get("propietarios"));
        verify(servicioMapaMock, times(1)).obtenerPropietarios();
    }

    @Test
    public void irAMapaDebeObtenerLasCoordenadasDeLatitudYLongitudActualesDelUsuario() throws JsonProcessingException {
        // preparación
        prepararControlador();
        Coordenada coordenada = new Coordenada();
        when(servicioMapaMock.obtenerUbicacionActual()).thenReturn(coordenada);

        // ejecucion
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, alquilerMock);

        // validacion
        assertEquals(coordenada.getLatitude(), modelAndView.getModel().get("latitudActual"));
        assertEquals(coordenada.getLongitude(), modelAndView.getModel().get("longitudActual"));
    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverUnaListaDePropietariosOrdenadosPorDistanciaALaUbicacionActual() throws JsonProcessingException {
        // preparación
        prepararControlador();
        Usuario propietario1 = mock(Usuario.class);
        when(propietario1.getLatitud()).thenReturn(3.0);
        when(propietario1.getLongitud()).thenReturn(3.0);
        Usuario propietario2 = mock(Usuario.class);
        when(propietario2.getLatitud()).thenReturn(2.0);
        when(propietario2.getLongitud()).thenReturn(2.0);
        Usuario propietario3 = mock(Usuario.class);
        when(propietario3.getLatitud()).thenReturn(1.0);
        when(propietario3.getLongitud()).thenReturn(1.0);
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(new LinkedList<>() {{
            add(propietario1);
            add(propietario2);
            add(propietario3);
        }});

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validación
        // el orden de la lista es propietario3, propietario2, propietario1
        List<Usuario> propietariosVista = (List<Usuario>) modelAndView.getModel().get("propietarios");
        assertEquals(propietario3, propietariosVista.get(0));
        assertEquals(propietario2, propietariosVista.get(1));
        assertEquals(propietario1, propietariosVista.get(2));
    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverUnaListaDeLasDistanciasALaUbicacionActual() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validación
        List<String> distancias = (List<String>) modelAndView.getModel().get("distancias");
        assertTrue(distancias.get(0).contains("metros") || distancias.get(0).contains("km"));
        assertTrue(distancias.get(1).contains("metros") || distancias.get(1).contains("km"));
        assertTrue(distancias.get(2).contains("metros") || distancias.get(2).contains("km"));
    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverElTiempoDeLlegadaCaminandoAUnDestinoDesdeLaUbicacionActual() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validación
        List<String> tiemposDeLlegadaCaminando = (List<String>) modelAndView.getModel().get("tiemposDeLlegadaCaminando");
        assertTrue(tiemposDeLlegadaCaminando.get(0).contains("min") || tiemposDeLlegadaCaminando.get(0).contains("h"));
    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverElTiempoDeLlegadaEnBicicletaAUnDestinoDesdeLaUbicacionActual() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validación
        List<String> tiemposDeLlegadaEnBicicleta = (List<String>) modelAndView.getModel().get("tiemposDeLlegadaEnBicicleta");
        assertTrue(tiemposDeLlegadaEnBicicleta.get(0).contains("min") || tiemposDeLlegadaEnBicicleta.get(0).contains("h"));

    }

    @Test
    public void irAMapaSinAlquilerEnCursoDebeDevolverElTiempoDeLlegadaEnAutoAUnDestinoDesdeLaUbicacionActual() throws JsonProcessingException {
        // preparación
        prepararControlador();

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock, null);

        // validación
        List<String> tiemposDeLlegadaEnAuto = (List<String>) modelAndView.getModel().get("tiemposDeLlegadaEnAuto");
        assertTrue(tiemposDeLlegadaEnAuto.get(0).contains("min") || tiemposDeLlegadaEnAuto.get(0).contains("h"));
    }

    private void prepararControlador() throws JsonProcessingException {
        // preparación
        Coordenada coordenadaMock = mock(Coordenada.class);
        when(servicioMapaMock.obtenerUbicacionActual()).thenReturn(coordenadaMock);
        List<Usuario> propietarios = new LinkedList<>() {{
            add(mock(Usuario.class));
            add(mock(Usuario.class));
            add(mock(Usuario.class));
        }};
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(propietarios);
        when(servicioMapaMock.obtenerDistancias(propietarios)).thenReturn(new LinkedList<>() {{
            add("1.0 km");
            add("2.0 km");
            add("3.0 km");
        }});
        when(servicioMapaMock.obtenerTiempoDeLlegadaCaminando(propietarios)).thenReturn(new LinkedList<>() {{
            add("10 min");
            add("20 min");
            add("30 min");
        }});
        when(servicioMapaMock.obtenerTiempoDeLlegadaEnBicicleta(propietarios)).thenReturn(new LinkedList<>() {{
            add("5 min");
            add("10 min");
            add("15 min");
        }});
        when(servicioMapaMock.obtenerTiempoDeLlegadaEnAuto(propietarios)).thenReturn(new LinkedList<>() {{
            add("2 min");
            add("4 min");
            add("6 min");
        }});
    }
}