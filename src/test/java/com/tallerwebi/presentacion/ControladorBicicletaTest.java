package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioResena;
import com.tallerwebi.presentacion.controladores.ControladorBicicleta;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorBicicletaTest {
    private ControladorBicicleta controladorBicicleta;
    private HttpSession sessionMock;
    private ServicioBicicleta servicioBicicletaMock;
    private ServicioResena servicioResenaMock;
    private Usuario usuarioMock;
    private Resena resenaMock;
    private Bicicleta bicicletaMock;

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        servicioBicicletaMock = mock(ServicioBicicleta.class);
        servicioResenaMock = mock(ServicioResena.class);
        controladorBicicleta = new ControladorBicicleta(servicioBicicletaMock, servicioResenaMock, sessionMock);
        usuarioMock = mock(Usuario.class);
        bicicletaMock = mock(Bicicleta.class);
        sessionMock.setAttribute("usuario", usuarioMock);
        resenaMock = mock(Resena.class);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
    }

    @Test
    public void irARegistrarBicicletaDebeDevolverLaVistaConBicicletaVacia() {
        // preparación
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecución
        ModelAndView modelAndView = controladorBicicleta.irARegistrarBicicleta();

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registrar-bicicleta"));
        assertThat(modelAndView.getModel().get("datosBicicleta"), instanceOf(DatosBicicleta.class));
    }

    @Test
    public void unUsuarioConRolPropietarioPuedeDarDeAltaUnaBicicleta() throws BicicletaValidacion {
        // preparación
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);

        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecución
        ModelAndView modelAndView = controladorBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
        verify(servicioBicicletaMock, times(1)).darDeAltaUnaBicicleta(datosBicicletaMock);
    }

    @Test
    public void siNoSeCargaronTodosLosDatosDeLaBicicletaDebeVolverAFormularioYMostrarError() throws BicicletaValidacion {
        // preparación
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);

        when(usuarioMock.getRol()).thenReturn("Propietario");
        doThrow(BicicletaValidacion.class).when(servicioBicicletaMock).darDeAltaUnaBicicleta(datosBicicletaMock);

        // ejecución
        ModelAndView modelAndView = controladorBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registrar-bicicleta"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe completar todos los campos"));
        verify(servicioBicicletaMock, times(1)).darDeAltaUnaBicicleta(datosBicicletaMock);
    }

    @Test
    public void unUsuarioConRolPropietarioPuedeDarDeBajaUnaBicicleta() throws BicicletaNoEncontrada {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);

        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecución
        ModelAndView modelAndView = controladorBicicleta.darDeBajaUnaBicicleta(bicicletaMock.getId());

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mis-bicicletas"));
        verify(servicioBicicletaMock, times(1)).darDeBajaUnaBicicleta(bicicletaMock.getId());
    }

    @Test
    public void unUsuarioConRolPropietarioPuedeVerSusBicicletasRegistradas() {
        // preparación
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getRol()).thenReturn("Propietario");

        // ejecución
        ModelAndView modelAndView = controladorBicicleta.irAMisBicicletas();

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mis-bicicletas"));

//        verify(servicioBicicletaMock, times(1)).obtenerBicicletasEnUsoPorIdUsuario(usuarioMock.getId());
//        verify(servicioBicicletaMock, times(1)).obtenerBicicletasEnReparacionPorIdUsuario(usuarioMock.getId());
//        verify(servicioBicicletaMock, times(1)).obtenerBicicletasDisponiblesPorIdUsuario(usuarioMock.getId());
    }

    @Test
    public void puedoVerElDetalleDeUnaBicicleta() throws BicicletaNoEncontrada {
        // Preparación
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getRol()).thenReturn("Cliente");
        // Simula una bicicleta con un ID específico (por ejemplo, ID 1)
        Bicicleta bicicletaConId1 = bicicletaMock;
        when(bicicletaConId1.getId()).thenReturn(1L);
        when(servicioBicicletaMock.obtenerBicicletaPorId(any())).thenReturn(bicicletaConId1);

        // Ejecución
        ModelAndView modelAndView = controladorBicicleta.detalleBicicleta(1L);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("detalle-bicicleta"));

        // Verifica que el objeto en el modelo sea un registro de tipo Bicicleta
        Bicicleta bicicletaEnModelo = (Bicicleta) modelAndView.getModel().get("bicicleta");
        assertThat(bicicletaEnModelo, instanceOf(Bicicleta.class));

        // Accede al ID de la bicicleta en el modelo y verifica que sea igual a 1
        assertThat(bicicletaEnModelo.getId(), equalTo(1L));
    }

    @Test
    public void puedoVerElDetalleDeUnaBicicletaConSusResenas() throws BicicletaNoEncontrada {
        // Preparación
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getRol()).thenReturn("Cliente");
        // Simula una bicicleta con un ID específico (por ejemplo, ID 1)

        Bicicleta bicicletaConId1 = new Bicicleta();

        bicicletaConId1.setId(1L);
        Resena resena = new Resena(7, "hola", bicicletaConId1, usuarioMock);
        List<Resena> resenas = new ArrayList<Resena>();
        resenas.add(resena);
        resenas.add(resenaMock);

        when(servicioBicicletaMock.obtenerBicicletaPorId(any())).thenReturn(bicicletaConId1);
        when(servicioResenaMock.obtenerResenasDeUnaBicicleta(bicicletaConId1)).thenReturn(resenas);

        List<Resena> listaResenas = servicioResenaMock.obtenerResenasDeUnaBicicleta(bicicletaConId1);
        // Ejecución
        ModelAndView modelAndView = controladorBicicleta.detalleBicicleta(1L);
        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("detalle-bicicleta"));
        // Verifica que el objeto en el modelo sea un registro de tipo Bicicleta
        Object bicicletaEnModelo = modelAndView.getModel().get("bicicleta");

        assertThat(bicicletaEnModelo, instanceOf(Bicicleta.class));
        // Accede al ID de la bicicleta en el modelo y verifica que sea igual a 1
        Bicicleta bicicletaEnModeloCast = (Bicicleta) bicicletaEnModelo;
        assertNotNull(listaResenas);
        assertThat(bicicletaEnModeloCast.getId(), equalTo(1L));
        assertThat(resenas.size(), equalTo(2));
        List<Resena> resenaEnModelo = (ArrayList<Resena>) modelAndView.getModel().get("resenas");
        assertThat(resenaEnModelo.size(), equalTo(2));
    }

    @Test
    public void traerLosDatosDeTodasLasBicis() {
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setCondicion(Condition.PERFECTO_ESTADO);
        List<Bicicleta> bicis = new ArrayList<>();
        bicis.add(bicicleta);
        when(servicioBicicletaMock.obtenerTodasLasBicicleta()).thenReturn(bicis);

        ModelAndView mv = controladorBicicleta.verBicicletas();

        assertEquals("bicicletas", mv.getViewName());
        assertEquals(1, mv.getModel().size());
        assertTrue(mv.getModel().keySet().contains("bicicletas"));
        assertEquals(1, ((List<Bicicleta>) mv.getModel().get("bicicletas")).size());
        assertEquals(bicicleta, ((List<Bicicleta>) mv.getModel().get("bicicletas")).get(0));
        assertEquals(Condition.PERFECTO_ESTADO, ((List<Bicicleta>) mv.getModel().get("bicicletas")).get(0).getCondicion());
    }

    @Test
    public void noExistenBicicletasEnviaMensajeDeError() {
        List<Bicicleta> bicis = new ArrayList<>();
        when(servicioBicicletaMock.obtenerTodasLasBicicleta()).thenReturn(bicis);

        ModelAndView mv = controladorBicicleta.verBicicletas();

        assertEquals("bicicletas", mv.getViewName());
        assertEquals(mv.getModel().size(), 1);
        assertTrue(mv.getModel().keySet().contains("error"));
    }

    @Test
    public void errorDesconocidoEnviaAPaginaDeErrorConCodigo() {
        when(servicioBicicletaMock.obtenerTodasLasBicicleta()).thenReturn(null);

        ModelAndView mv = controladorBicicleta.verBicicletas();

        assertEquals("error", mv.getViewName());
        assertEquals(mv.getModel().size(), 1);
        assertTrue(mv.getModel().keySet().contains("error"));
    }

    @Test
    public void irALasBicicletasDelPropietarioDebeDevolverUnaListaConTodasLasBicicletasDisponibles() {
        // preparación
        Usuario propietarioMock = mock(Usuario.class);

        Bicicleta bicicleta1 = mock(Bicicleta.class);
        when(bicicleta1.getUsuario()).thenReturn(propietarioMock);
        when(bicicleta1.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);

        Bicicleta bicicleta2 = mock(Bicicleta.class);
        when(bicicleta2.getUsuario()).thenReturn(propietarioMock);
        when(bicicleta2.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);

        List<Bicicleta> bicicletasMock = new ArrayList<>();
        bicicletasMock.add(bicicleta1);
        bicicletasMock.add(bicicleta2);

        when(servicioBicicletaMock.obtenerBicicletasDisponiblesPorIdUsuario(propietarioMock.getId())).thenReturn(bicicletasMock);

        // ejecución
        ModelAndView modelAndView = controladorBicicleta.bicicletasDelPropietario(propietarioMock.getId());

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("bicicletas"));
        assertThat(modelAndView.getModel().get("bicicletas"), instanceOf(List.class));
        assertThat(((List<Bicicleta>) modelAndView.getModel().get("bicicletas")).size(), equalTo(2));
        verify(servicioBicicletaMock, times(1)).obtenerBicicletasDisponiblesPorIdUsuario(usuarioMock.getId());
    }
}
