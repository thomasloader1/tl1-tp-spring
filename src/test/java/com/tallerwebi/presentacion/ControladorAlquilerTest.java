package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.servicios.ServicioAlquiler;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.presentacion.controladores.ControladorAlquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ControladorAlquilerTest {

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
        usuarioMock = mock(Usuario.class);

        sessionMock.setAttribute("usuario", usuarioMock);

        controladorAlquiler = new ControladorAlquiler(servicioAlquilerMock, servicioBicicletaMock);
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


    @Test
    public void testVerAlquilerExitoso() throws BicicletaNoEncontrada {
        // Configura tus objetos de prueba, como idBicicleta, usuario, datosAlquiler, bicicleta y alquileres
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        DatosAlquiler datosAlquiler = new DatosAlquiler();
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setId(1L);
        List<Alquiler> alquileres = new ArrayList<>();

        // Configura el comportamiento simulado para obtener una bicicleta y alquileres exitosamente
        Mockito.when(servicioBicicletaMock.obtenerBicicletaPorId(Mockito.eq(bicicleta.getId()))).thenReturn(bicicleta);
        Mockito.when(servicioAlquilerMock.buscarAlquiler(Mockito.eq(datosAlquiler))).thenReturn(alquileres);

        // Llama al método del controlador
        ModelAndView modelAndView = controladorAlquiler.verAlquiler(bicicleta.getId(), usuario, datosAlquiler);

        Usuario userInModel = (Usuario) modelAndView.getModel().get("usuario");

        // Verifica las aserciones
        Assertions.assertEquals("mis-alquileres", modelAndView.getViewName());
        Assertions.assertSame(usuario.getId(), userInModel.getId());
        Assertions.assertSame(bicicleta, modelAndView.getModel().get("bicicleta"));
        Assertions.assertSame(alquileres, modelAndView.getModel().get("alquileres"));
    }

    @Test
    public void testVerAlquilerError() throws BicicletaNoEncontrada {
        // Configura tus objetos de prueba y el comportamiento simulado para lanzar una excepción
        Long idBicicleta = 1L;
        Usuario usuario = new Usuario();
        DatosAlquiler datosAlquiler = new DatosAlquiler();

        // Configura el comportamiento simulado para lanzar una excepción
        when(servicioBicicletaMock.obtenerBicicletaPorId(eq(idBicicleta))).thenThrow(BicicletaNoEncontrada.class);

        // Llama al método del controlador
        ModelAndView modelAndView = controladorAlquiler.verAlquiler(idBicicleta, usuario, datosAlquiler);

        // Verifica las aserciones
        Assertions.assertEquals("mis-alquileres", modelAndView.getViewName());
        Assertions.assertEquals("Error al mostrar el Alquiler", modelAndView.getModel().get("error"));
    }
}
