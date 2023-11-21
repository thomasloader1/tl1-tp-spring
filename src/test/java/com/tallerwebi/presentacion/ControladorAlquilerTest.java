package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.servicios.ServicioAlquiler;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioMercadoPago;
import com.tallerwebi.presentacion.controladores.ControladorAlquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosPreferencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.util.CollectionUtils.isEmpty;

public class
ControladorAlquilerTest {
    private ControladorAlquiler controladorAlquiler;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioBicicleta servicioBicicletaMock;
    private ServicioAlquiler servicioAlquilerMock;
    private ServicioMercadoPago servicioMercadoPagoMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        servicioAlquilerMock = mock(ServicioAlquiler.class);
        servicioMercadoPagoMock = mock(ServicioMercadoPago.class);
        controladorAlquiler = new ControladorAlquiler(servicioAlquilerMock, servicioBicicletaMock, servicioMercadoPagoMock, sessionMock);
        usuarioMock = mock(Usuario.class);
        sessionMock.setAttribute("usuario", usuarioMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
    }

    @Test
    public void QueSePuedaCrearUnAlquiler() throws AlquilerValidacion, MPException, MPApiException, BicicletaNoEncontrada {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(usuarioMock.getRol()).thenReturn("Cliente");

        Alquiler alquilerMock = mock(Alquiler.class);
        when(servicioAlquilerMock.comenzarAlquiler(datosAlquilerMock)).thenReturn(alquilerMock);

        DatosPreferencia preferenceMock = mock(DatosPreferencia.class);
        when(servicioMercadoPagoMock.crearPreferenciaPago(alquilerMock)).thenReturn(preferenceMock);

        // ejecución
        controladorAlquiler.crearAlquiler(bicicletaMock.getId(), datosAlquilerMock);

        // validación
        verify(servicioAlquilerMock, times(1)).comenzarAlquiler(datosAlquilerMock);
        verify(requestMock.getSession(), times(1)).setAttribute(eq("alquilerAux"), any(Alquiler.class));
        verify(servicioMercadoPagoMock, times(1)).crearPreferenciaPago(alquilerMock);
    }

    @Test
    public void QueSePuedaFinalizarUnAlquilerYEnvieALaPantallaDeResenia() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Alquiler alquilerMock = mock(Alquiler.class);
        when(bicicletaMock.getId()).thenReturn(1L);
        when(servicioAlquilerMock.obtenerBicicletaPorIdDeAlquiler(anyLong())).thenReturn(bicicletaMock);
        when(sessionMock.getAttribute("alquiler")).thenReturn(alquilerMock);

        // ejecución
        ModelAndView modelAndView = controladorAlquiler.finalizarAlquiler(alquilerMock.getId());

        // validación
        String viewName = "redirect:/bicicleta/" + bicicletaMock.getId() + "/crear-resena";
        assertThat(modelAndView.getViewName(), equalToIgnoringCase(viewName));
        verify(servicioAlquilerMock, times(1)).finalizarAlquiler(alquilerMock.getId());
    }

    @Test
    public void testVerAlquiler() {
        // Crea un usuario y un objeto de datos de alquiler para las pruebas
        DatosAlquiler datosAlquiler = new DatosAlquiler();

        // Llama al método del controlador
        ModelAndView modelAndView = controladorAlquiler.verAlquiler(datosAlquiler);

        // Verifica que el modelo contenga los valores esperados
        Assertions.assertEquals(usuarioMock, modelAndView.getModel().get("usuario"));
        List<Alquiler> alquileres = (List<Alquiler>) modelAndView.getModel().get("alquileres");
        Assertions.assertNotNull(alquileres);

        // Verifica que la vista se muestre correctamente
        Assertions.assertEquals("mis-alquileres", modelAndView.getViewName());
    }

    @Test
    public void siVoyAMisAlquileresNoTengoAlquileresParaMostrar() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        when(datosAlquilerMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución
        ModelAndView modelAndView = controladorAlquiler.verAlquiler(datosAlquilerMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mis-alquileres"));
        assertTrue(isEmpty((List<Alquiler>) modelAndView.getModel().get("alquileres")));
        verify(servicioAlquilerMock, times(1)).obtenerAlquileresDelUsuario(datosAlquilerMock);
    }
}
