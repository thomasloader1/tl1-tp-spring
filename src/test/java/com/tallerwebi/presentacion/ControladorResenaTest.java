package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioResena;
import com.tallerwebi.presentacion.controladores.ControladorResena;
import com.tallerwebi.presentacion.dto.DatosResena;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorResenaTest {
    private HttpSession sessionMock;
    private ControladorResena controladorResena;
    private ServicioResena servicioResenaMock;
    private ServicioBicicleta servicioBicicletaMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        servicioResenaMock = mock(ServicioResena.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        controladorResena = new ControladorResena(servicioResenaMock, servicioBicicletaMock, sessionMock);
        usuarioMock = mock(Usuario.class);
        sessionMock.setAttribute("usuario", usuarioMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
    }

    @Test
    public void irACrearResenaDeUnaBicicletaDebeDevolverLaVistaConElFormularioDeResenaVacio() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(usuarioMock.getRol()).thenReturn("Cliente");

        // ejecución
        ModelAndView modelAndView = controladorResena.irACrearResena(bicicletaMock.getId());

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("crear-resena"));
        assertThat(modelAndView.getModel().get("datosResena"), instanceOf(DatosResena.class));
    }

    @Test
    public void queSePuedaCrearUnaResenaDeUnaBicicleta() throws ResenaValidacion, ResenaPuntajeValidacion {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(usuarioMock.getRol()).thenReturn("Cliente");

        // ejecución
        ModelAndView modelAndView = controladorResena.subirResena(bicicletaMock.getId(), datosResenaMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(servicioResenaMock, times(1)).subirResena(datosResenaMock);
    }

    @Test
    public void siNoSeCargaronTodosLosDatosDeLaResenaDebeVolverAFormularioYMostrarError() throws ResenaValidacion, ResenaPuntajeValidacion {
        // preparacion
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(usuarioMock.getRol()).thenReturn("Cliente");
        doThrow(ResenaValidacion.class).when(servicioResenaMock).subirResena(datosResenaMock);

        // ejecucion
        ModelAndView modelAndView = controladorResena.subirResena(bicicletaMock.getId(), datosResenaMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("crear-resena"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe completar todos los campos"));
        verify(servicioResenaMock, times(1)).subirResena(datosResenaMock);
    }

    @Test
    public void queSePuedaObtenerUnListadoDeLasResenasDeUnaBicicleta() throws BicicletaNoEncontrada {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(servicioBicicletaMock.obtenerBicicletaPorId(anyLong())).thenReturn(bicicletaMock);
        when(usuarioMock.getRol()).thenReturn("Cliente");
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorResena.irAResenasDeUnaBicicleta(bicicletaMock.getId());

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("resenas"));
        assertThat(modelAndView.getModel().get("resenas"), instanceOf(List.class));
        verify(servicioResenaMock, times(1)).obtenerResenasDeUnaBicicleta(bicicletaMock);
    }

}
