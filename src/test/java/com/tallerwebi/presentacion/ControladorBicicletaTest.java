package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.ServicioBicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorBicicletaTest {
    private ControladorBicicleta controladorBicicleta;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioBicicleta servicioBicicletaMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        controladorBicicleta = new ControladorBicicleta(servicioBicicletaMock);
        usuarioMock = mock(Usuario.class);
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

//    @Test
//    public void cuandoUnaBicicletaEsNuevaNoDeberiaTenerResenias() {
//        // Preparación
//        Bicicleta bici = new Bicicleta(1, EstadoBicicleta.DISPONIBLE);
//
//        servicioBicicleta.agregarBicicleta(bici);
//
//        // Lógica del test
//        List<Resenia> cantidadResenias = servicioBicicleta.verReseniasDeBicicleta(bici.getId());
//
//        // Verificación
//        assertEquals(0, cantidadResenias.size());
//    }
//
//    @Test
//    public void cargoUnaReseniaAUnaBicileta() {
//        // Preparación
//        Bicicleta bici = new Bicicleta(1, EstadoBicicleta.DISPONIBLE);
//
//        servicioBicicleta.agregarBicicleta(bici);
//
//        // Configura el servicio para agregar la reseña
//        Resenia resenia = new Resenia(1, "Esta bicicleta es una pija", new Date(), bici.getId());
//
//        // Lógica del test
//        boolean agregada = servicioBicicleta.agregarResenia(resenia);
//        List<Resenia> reseniasDeBicicleta = servicioBicicleta.verReseniasDeBicicleta(bici.getId());
//
//        // Verificación
//        assertTrue(agregada); // Verifica que se haya agregado la reseña con éxito
//        assertEquals(1, reseniasDeBicicleta.size()); // Verifica que haya una reseña para la bicicleta
//    }
}
