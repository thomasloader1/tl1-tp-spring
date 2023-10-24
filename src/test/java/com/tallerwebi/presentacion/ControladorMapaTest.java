package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import com.tallerwebi.presentacion.controladores.ControladorMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        ModelAndView modelAndView = controladorMapa.irAMapa();

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mapa"));
    }

    @Test
    public void irAMapaDebeDevolverLaVistaDeMapaConLosPropietarios() {
        // ejecucion
        ModelAndView modelAndView = controladorMapa.irAMapa();

        // validacion
        assertNotNull(modelAndView.getModel().get("propietarios"));
        verify(servicioMapaMock, times(1)).obtenerPropietarios();
    }
}