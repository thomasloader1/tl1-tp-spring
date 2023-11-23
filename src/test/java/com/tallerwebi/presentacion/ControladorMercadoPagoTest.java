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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorMercadoPagoTest {
    private ControladorAlquiler controladorAlquiler;
    private ServicioMercadoPago servicioMercadoPago;
    private ServicioBicicleta servicioBicicleta;
    private ServicioAlquiler servicioAlquiler;
    private Usuario usuarioMock;
    private HttpSession sessionMock;
    private HttpServletRequest requestMock;

    @BeforeEach
    public void init() {
        servicioBicicleta = mock(ServicioBicicleta.class);
        servicioAlquiler = mock(ServicioAlquiler.class);
        servicioMercadoPago = mock(ServicioMercadoPago.class);

        usuarioMock = mock(Usuario.class);
        sessionMock = mock(HttpSession.class);
        requestMock = mock(HttpServletRequest.class);
        controladorAlquiler = new ControladorAlquiler(servicioAlquiler, servicioBicicleta, servicioMercadoPago, sessionMock);
    }

    @Test
    public void queSeRedireccioneCorrectamente() throws MPException, MPApiException, AlquilerValidacion, BicicletaNoEncontrada {
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Usuario usuarioMock = mock(Usuario.class);
        when(bicicletaMock.getId()).thenReturn(1L);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);
        DatosPreferencia datosPreferenciaMock = new DatosPreferencia("1234", "Matias", "Rojas", "1530664068", "fecha", "urlMercadoPago");
        when(servicioMercadoPago.crearPreferenciaPago(any())).thenReturn(datosPreferenciaMock);
        ModelAndView modelAndView = controladorAlquiler.crearAlquiler(bicicletaMock.getId(), datosAlquilerMock);
        DatosPreferencia responseMock = (DatosPreferencia) modelAndView.getModel().get("idPreferencia");
        assertEquals("urlMercadoPago", responseMock.urlCheckout);
        assertEquals(modelAndView.getModel().get("idPreferencia"), datosPreferenciaMock);
    }

    @Test
    public void queMuestreMensajeDeErrorSiNoEstaAprobado() throws MPException, MPApiException, BicicletaNoEncontrada, AlquilerValidacion {
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Alquiler alquilerMock = mock(Alquiler.class);
        when(alquilerMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);
        when(sessionMock.getAttribute("alquilerAux")).thenReturn(alquilerMock);
        when(servicioAlquiler.comenzarAlquiler(datosAlquilerMock)).thenReturn(alquilerMock);
        DatosPreferencia datosPreferenciaMock = new DatosPreferencia("1234", "Matias", "Rojas", "1530664068", "fecha", "urlMercadoPago");
        when(servicioMercadoPago.crearPreferenciaPago(any())).thenReturn(datosPreferenciaMock);
        ModelAndView modelAndView = controladorAlquiler.crearAlquiler(bicicletaMock.getId(), datosAlquilerMock);
        assertEquals("redirect:" + datosPreferenciaMock.urlCheckout, modelAndView.getViewName());
        when(requestMock.getParameter("status")).thenReturn("rejected");
        modelAndView = controladorAlquiler.validarPago(String.valueOf(requestMock));
        assertEquals("redirect:/bicicleta/" + bicicletaMock.getId() + "/detalle", modelAndView.getViewName());
        assertEquals("Error al procesar el pago.", modelAndView.getModel().get("error"));
        assertEquals(bicicletaMock, modelAndView.getModel().get("bicicleta"));
    }
}
