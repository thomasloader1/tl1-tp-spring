package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioAlquiler;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.presentacion.controladores.ControladorAlquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ControladorAlquierTest {

    private ControladorAlquiler controladorAlquiler;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioBicicleta servicioBicicletaMock;
    private ServicioAlquiler servicioAlquilerMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        servicioAlquilerMock = mock(ServicioAlquiler.class);
        controladorAlquiler = new ControladorAlquiler(servicioAlquilerMock, servicioBicicletaMock);
        usuarioMock = mock(Usuario.class);
        sessionMock.setAttribute("usuario", usuarioMock);
    }

    @Test
    public void QueSePuedaCrearUnAlquiler(){
            // preparación

            Bicicleta bicicletaMock = mock(Bicicleta.class);
            DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
            when(requestMock.getSession()).thenReturn(sessionMock);
            when(usuarioMock.getRol()).thenReturn("Cliente");

            // ejecución
            ModelAndView modelAndView = controladorAlquiler.crearAlquiler(bicicletaMock.getId(), usuarioMock, datosAlquilerMock);

            // validación
            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mapa"));
            verify(servicioAlquilerMock, times(1)).crearAlquiler(datosAlquilerMock);
        }

    @Test
    public void QueSePuedaFinalizarUnAlquilerYEnvieALaPantallaDeResenia() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Alquiler alquilerMock = mock(Alquiler.class);
        when(bicicletaMock.getId()).thenReturn(1L);
        when(servicioAlquilerMock.obtenerBicicletaPorIdDeAlquiler(anyLong())).thenReturn(bicicletaMock);
        when(requestMock.getSession()).thenReturn(sessionMock);


        // ejecución
        ModelAndView modelAndView = controladorAlquiler.finalizarAlquiler(alquilerMock.getId());


        // validación
        String viewName = "redirect:/bicicleta/" + bicicletaMock.getId() + "/crear-resena";
        assertThat(modelAndView.getViewName(), equalToIgnoringCase(viewName));
        verify(servicioAlquilerMock, times(1)).finalizarAlquiler(alquilerMock.getId());
    }
}
