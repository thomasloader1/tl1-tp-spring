package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.presentacion.controladores.ControladorBicicleta;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorBicicletaTest {
    private ControladorBicicleta controladorBicicleta;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioBicicleta servicioBicicletaMock;
    private Usuario usuarioMock;
    private Bicicleta bicicletaMock;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        controladorBicicleta = new ControladorBicicleta(servicioBicicletaMock);
        usuarioMock = mock(Usuario.class);
        bicicletaMock = mock(Bicicleta.class);
        sessionMock.setAttribute("usuario", usuarioMock);
    }

    @Test
    public void irARegistrarBicicletaDebeDevolverLaVistaConBicicletaVacia() {
        // preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecucion
        ModelAndView modelAndView = controladorBicicleta.irARegistrarBicicleta(usuarioMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registrar-bicicleta"));
        assertThat(modelAndView.getModel().get("datosBicicleta"), instanceOf(DatosBicicleta.class));
    }

    @Test
    public void unUsuarioConRolPropietarioPuedeDarDeAltaUnaBicicleta() throws BicicletaValidacion {
        // preparacion
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecucion
        ModelAndView modelAndView = controladorBicicleta.darDeAltaUnaBicicleta(usuarioMock, datosBicicletaMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(servicioBicicletaMock, times(1)).darDeAltaUnaBicicleta(datosBicicletaMock);
    }

    @Test
    public void siNoSeCargaronTodosLosDatosDeLaBicicletaDebeVolverAFormularioYMostrarError() throws BicicletaValidacion {
        // preparacion
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");
        doThrow(BicicletaValidacion.class).when(servicioBicicletaMock).darDeAltaUnaBicicleta(datosBicicletaMock);

        // ejecucion
        ModelAndView modelAndView = controladorBicicleta.darDeAltaUnaBicicleta(usuarioMock, datosBicicletaMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registrar-bicicleta"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe completar todos los campos"));
        verify(servicioBicicletaMock, times(1)).darDeAltaUnaBicicleta(datosBicicletaMock);
    }

    @Test
    public void unUsuarioConRolPropietarioPuedeDarDeBajaUnaBicicleta() {
        // preparacion
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecucion
        ModelAndView modelAndView = controladorBicicleta.darDeBajaUnaBicicleta(bicicletaMock.getId());

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mis-bicicletas"));
        verify(servicioBicicletaMock, times(1)).darDeBajaUnaBicicleta(bicicletaMock.getId());
    }

    @Test
    public void unUsuarioConRolPropietarioPuedeVerSusBicicletasRegistradas() {
        // preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecucion
        ModelAndView modelAndView = controladorBicicleta.irAMisBicicletas(usuarioMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mis-bicicletas"));
        verify(servicioBicicletaMock, times(1)).obtenerBicicletasDelUsuario(usuarioMock);
    }

    @Test
    public void puedoVerElDetalleDeUnaBicicleta() throws BicicletaNoEncontrada {
        // Preparación
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getRol()).thenReturn("Cliente");
        // Simula una bicicleta con un ID específico (por ejemplo, ID 1)
        Bicicleta bicicletaConId1 = bicicletaMock;
        //bicicletaConId1.setId(1L);
        when(bicicletaConId1.getId()).thenReturn(1L);

        when(servicioBicicletaMock.obtenerBicicletaPorId(any())).thenReturn(bicicletaConId1);

        // Ejecución
        ModelAndView modelAndView = controladorBicicleta.detalleBicicleta(1);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("detalle-bicicleta"));

        // Verifica que el objeto en el modelo sea un registro de tipo Bicicleta
        Bicicleta bicicletaEnModelo = (Bicicleta) modelAndView.getModel().get("bicicleta");
        assertThat(bicicletaEnModelo, instanceOf(Bicicleta.class));

        // Accede al ID de la bicicleta en el modelo y verifica que sea igual a 1
        assertThat(bicicletaEnModelo.getId(), equalTo(1L));
    }
}
