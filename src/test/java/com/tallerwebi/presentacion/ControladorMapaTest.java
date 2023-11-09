package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import com.tallerwebi.presentacion.controladores.ControladorMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import com.tallerwebi.presentacion.controladores.ControladorMapa;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class ControladorMapaTest {
    private ControladorMapa controladorMapa;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioMapa servicioMapaMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioMapaMock = mock(ServicioMapa.class);
        controladorMapa = new ControladorMapa(servicioMapaMock);
        usuarioMock = mock(Usuario.class);
        sessionMock.setAttribute("usuario", usuarioMock);
    }

    @Test
    public void irAMapaDebeDevolverLaVistaDeMapa() {
        // ejecución

        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);


        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mapa"));
    }

    @Test
    public void irAMapaDebeDevolverLaVistaDeMapaConLosPropietarios() {
        // ejecucion

        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);


        // validacion
        assertNotNull(modelAndView.getModel().get("propietarios"));
        verify(servicioMapaMock, times(1)).obtenerPropietarios();
    }

    @Test
    public void irAMapaDebeObtenerLasCoordenadasDeLatitudYLongitudActualesDelUsuario() {
        // preparación
        Coordenada coordenada = obtenerUbicacionActual();

        // ejecucion
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);

        // validacion
        assertEquals(coordenada.getLatitude(), modelAndView.getModel().get("latitudActual"));
        assertEquals(coordenada.getLongitude(), modelAndView.getModel().get("longitudActual"));
    }

    @Test
    public void irAMapaDebeDevolverUnaListaDePropietariosOrdenadosPorDistanciaALaUbicacionActual() {
        // preparación
        Coordenada coordenada = obtenerUbicacionActual();
        Usuario propietario1 = mock(Usuario.class);
        Usuario propietario2 = mock(Usuario.class);
        Usuario propietario3 = mock(Usuario.class);
        when(propietario1.getLatitud()).thenReturn(coordenada.getLatitude() + 0.03);
        when(propietario1.getLongitud()).thenReturn(coordenada.getLongitude() + 0.03);
        when(propietario2.getLatitud()).thenReturn(coordenada.getLatitude() + 0.02);
        when(propietario2.getLongitud()).thenReturn(coordenada.getLongitude() + 0.02);
        when(propietario3.getLatitud()).thenReturn(coordenada.getLatitude() + 0.01);
        when(propietario3.getLongitud()).thenReturn(coordenada.getLongitude() + 0.01);
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(new LinkedList<>() {{
            add(propietario1);
            add(propietario2);
            add(propietario3);
        }});

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);

        // validación
        // el orden de la lista es propietario3, propietario2, propietario1
        List<Usuario> propietarios = (List<Usuario>) modelAndView.getModel().get("propietarios");
        assertEquals(propietario3, propietarios.get(0));
        assertEquals(propietario2, propietarios.get(1));
        assertEquals(propietario1, propietarios.get(2));
    }

    @Test
    public void irAMapaDebeDevolverUnaListaDeLasDistanciasALaUbicacionActual() {
        // preparación
        Coordenada coordenada = obtenerUbicacionActual();
        Usuario propietario1 = mock(Usuario.class);
        Usuario propietario2 = mock(Usuario.class);
        Usuario propietario3 = mock(Usuario.class);
        when(propietario1.getLatitud()).thenReturn(coordenada.getLatitude() + 0.03);
        when(propietario1.getLongitud()).thenReturn(coordenada.getLongitude() + 0.03);
        when(propietario2.getLatitud()).thenReturn(coordenada.getLatitude() + 0.02);
        when(propietario2.getLongitud()).thenReturn(coordenada.getLongitude() + 0.02);
        when(propietario3.getLatitud()).thenReturn(coordenada.getLatitude() + 0.01);
        when(propietario3.getLongitud()).thenReturn(coordenada.getLongitude() + 0.01);
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(new LinkedList<>() {{
            add(propietario1);
            add(propietario2);
            add(propietario3);
        }});

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);

        // validación
        List<String> distancias = (List<String>) modelAndView.getModel().get("distancias");
        assertTrue(distancias.get(0).contains("metros") || distancias.get(0).contains("km"));
        assertTrue(distancias.get(1).contains("metros") || distancias.get(1).contains("km"));
        assertTrue(distancias.get(2).contains("metros") || distancias.get(2).contains("km"));
    }

    @Test
    public void irAMapaDebeDevolverElTiempoDeLlegadaCaminandoAUnDestinoDesdeLaUbicacionActual() {
        // preparación
        Coordenada coordenada = obtenerUbicacionActual();
        Usuario propietario1 = mock(Usuario.class);
        when(propietario1.getLatitud()).thenReturn(coordenada.getLatitude() + 0.03);
        when(propietario1.getLongitud()).thenReturn(coordenada.getLongitude() + 0.03);
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(new LinkedList<>() {{
            add(propietario1);
        }});

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);

        // validación
        List<String> tiemposDeLlegadaCaminando = (List<String>) modelAndView.getModel().get("tiemposDeLlegadaCaminando");
        assertTrue(tiemposDeLlegadaCaminando.get(0).contains("min"));
    }

    @Test
    public void irAMapaDebeDevolverElTiempoDeLlegadaEnBicicletaAUnDestinoDesdeLaUbicacionActual() {
        // preparación
        Coordenada coordenada = obtenerUbicacionActual();
        Usuario propietario1 = mock(Usuario.class);
        when(propietario1.getLatitud()).thenReturn(coordenada.getLatitude() + 0.03);
        when(propietario1.getLongitud()).thenReturn(coordenada.getLongitude() + 0.03);
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(new LinkedList<>() {{
            add(propietario1);
        }});

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);

        // validación
        List<String> tiemposDeLlegadaCaminando = (List<String>) modelAndView.getModel().get("tiemposDeLlegadaEnBicicleta");
        assertTrue(tiemposDeLlegadaCaminando.get(0).contains("min"));
    }

    @Test
    public void irAMapaDebeDevolverElTiempoDeLlegadaEnAutoAUnDestinoDesdeLaUbicacionActual() {
        // preparación
        Coordenada coordenada = obtenerUbicacionActual();
        Usuario propietario1 = mock(Usuario.class);
        when(propietario1.getLatitud()).thenReturn(coordenada.getLatitude() + 0.03);
        when(propietario1.getLongitud()).thenReturn(coordenada.getLongitude() + 0.03);
        when(servicioMapaMock.obtenerPropietarios()).thenReturn(new LinkedList<>() {{
            add(propietario1);
        }});

        // ejecución
        ModelAndView modelAndView = controladorMapa.irAMapa(usuarioMock);

        // validación
        List<String> tiemposDeLlegadaCaminando = (List<String>) modelAndView.getModel().get("tiemposDeLlegadaEnAuto");
        assertTrue(tiemposDeLlegadaCaminando.get(0).contains("min"));
    }


    private Coordenada obtenerUbicacionActual() {
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("IPDATA_API_KEY");
        String apiUrl = "https://api.ipdata.co?fields=latitude,longitude&api-key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Coordenada> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Coordenada.class);
        return response.getBody();
    }
}