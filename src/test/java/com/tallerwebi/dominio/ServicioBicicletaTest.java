package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.BicicletaStatus;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.servicios.ServicioBicicletaImpl;
import com.tallerwebi.dominio.servicios.ServicioVehicleStatus;
import com.tallerwebi.dominio.servicios.ServicioVehicleStatusIMP;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.infraestructura.repositorios.RepositorioVehicleStatus;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioBicicletaTest {
    private ServicioBicicletaImpl servicioBicicleta;
    private ServicioVehicleStatus servicioVehicleStatus;
    private RepositorioVehicleStatus vehicleStatusRepositorio;
    private RepositorioBicicleta repositorioBicicletaMock;

    @BeforeEach
    public void init() {
        servicioVehicleStatus = new ServicioVehicleStatusIMP();
        vehicleStatusRepositorio = mock(RepositorioVehicleStatus.class);
        repositorioBicicletaMock = mock(RepositorioBicicleta.class);
        servicioBicicleta = new ServicioBicicletaImpl(repositorioBicicletaMock, vehicleStatusRepositorio, servicioVehicleStatus);
    }


    @Test
    public void queSiUnaBicicletaPresentaAlgunFalloSuEstadoCambieARequiereReparacion(){
        //Preparacion
        Bicicleta bici = new Bicicleta();
        BicicletaStatus estado = makeStatus();

        estado.setHandler_hardness(100);
        bici.setStatus(estado);
        bici.setEstadoBicicleta(EstadoBicicleta.EN_USO);

        //Ejecucion
        servicioBicicleta.actualizarEstadoBicicleta(bici);

        assertEquals(bici.getEstadoBicicleta(),EstadoBicicleta.REQUIERE_REPARACION);
    }

    @Test
    public void queSePuedaDarDeAltaUnaBicicleta() throws BicicletaValidacion {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(datosBicicletaMock.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);
        when(datosBicicletaMock.getDescripcion()).thenReturn("Bicicleta de paseo");
        when(datosBicicletaMock.getUsuario()).thenReturn(usuarioMock);
        when(datosBicicletaMock.getPrecioVenta()).thenReturn(50000.0);

        // ejecución
        servicioBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock);

        // validación
        verify(repositorioBicicletaMock, times(1)).registrarBicicleta(any(Bicicleta.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElEstadoDeBicicletaEsNuloYNoSePuedeDarDeAltaUnaBicicleta() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(datosBicicletaMock.getEstadoBicicleta()).thenReturn(null);
        when(datosBicicletaMock.getDescripcion()).thenReturn("Bicicleta de paseo");
        when(datosBicicletaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(BicicletaValidacion.class, () -> servicioBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock));
        verify(repositorioBicicletaMock, times(0)).registrarBicicleta(any(Bicicleta.class));
    }

    @Test
    public void queLanceUnaExcepcionSiLaDescripcionDeBicicletaEsVaciaxYNoSePuedeDarDeAltaUnaBicicleta() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosBicicleta datosBicicletaMock = mock(DatosBicicleta.class);
        when(datosBicicletaMock.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);
        when(datosBicicletaMock.getDescripcion()).thenReturn("");
        when(datosBicicletaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(BicicletaValidacion.class, () -> servicioBicicleta.darDeAltaUnaBicicleta(datosBicicletaMock));
        verify(repositorioBicicletaMock, times(0)).registrarBicicleta(any(Bicicleta.class));
    }

    @Test
    public void queLanceUnaExcepcionSiNoSeEncuentraUnaBicicletaConId() {
        // preparación
        when(repositorioBicicletaMock.obtenerBicicletaPorId(anyLong())).thenReturn(null);

        // ejecución y validación
        assertThrows(BicicletaNoEncontrada.class, () -> servicioBicicleta.obtenerBicicletaPorId(anyLong()));
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletaPorId(anyLong());
    }

    @Test
    public void queSePuedaDarDeBajaUnaBicicleta() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletaPorId(anyLong())).thenReturn(bicicletaMock);

        // ejecución
        servicioBicicleta.darDeBajaUnaBicicleta(bicicletaMock.getId());

        // validación
        verify(repositorioBicicletaMock, times(1)).eliminarBicicleta(any(Bicicleta.class));
    }

    @Test
    public void queSePuedaObtenerUnaBicicletaPorId() throws BicicletaNoEncontrada {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletaPorId(anyLong())).thenReturn(bicicletaMock);

        // ejecución
        Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(bicicletaMock.getId());

        // validación
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletaPorId(anyLong());
        assertEquals(bicicletaMock, bicicleta);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletas() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(repositorioBicicletaMock.obtenerBicicletas()).thenReturn(List.of(bicicletaMock));

        // ejecución
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerTodasLasBicicleta();

        // validación
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletas();
        assertEquals(1, bicicletas.size());
        assertEquals(bicicletaMock, bicicletas.get(0));
    }
    @Test
    public void queSePuedaObtenerListaDeLasBicicletasDisponibles(){
        //preparacion
        Bicicleta bicicletaUnoMock = mock(Bicicleta.class);
        // ejecucion
        when(repositorioBicicletaMock.obtenerBicicletasDisponibles()).thenReturn(Arrays.asList(bicicletaUnoMock));
        List <Bicicleta> bicicletas = servicioBicicleta.obtenerBicicletasDisponibles();
        //validacion
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletasDisponibles();
        assertEquals(1, bicicletas.size());
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletasDelUsuario() {
        // preparación
        Usuario propietarioMock = mock(Usuario.class);

        Bicicleta bicicleta1 = mock(Bicicleta.class);
        when(bicicleta1.getUsuario()).thenReturn(propietarioMock);

        Bicicleta bicicleta2 = mock(Bicicleta.class);
        when(bicicleta2.getUsuario()).thenReturn(propietarioMock);

        List<Bicicleta> bicicletasMock = new ArrayList<>();
        bicicletasMock.add(bicicleta1);
        bicicletasMock.add(bicicleta2);

        when(repositorioBicicletaMock.obtenerBicicletasDelUsuario(propietarioMock)).thenReturn(bicicletasMock);

        // ejecución
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerBicicletasDelUsuario(propietarioMock);

        // validación
        assertEquals(2, bicicletas.size());
        assertEquals(bicicleta1, bicicletas.get(0));
        assertEquals(bicicleta2, bicicletas.get(1));
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletasDelUsuario(propietarioMock);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletasDisponiblesDeUnPropietario() {
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

        when(repositorioBicicletaMock.obtenerBicicletasDisponiblesPorIdUsuario(propietarioMock.getId())).thenReturn(bicicletasMock);

        // ejecución
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(propietarioMock.getId());

        // validación
        assertEquals(2, bicicletas.size());
        assertEquals(bicicleta1, bicicletas.get(0));
        assertEquals(bicicleta2, bicicletas.get(1));
        verify(repositorioBicicletaMock, times(1)).obtenerBicicletasDisponiblesPorIdUsuario(propietarioMock.getId());
    }

    private BicicletaStatus makeStatus() {
        BicicletaStatus status = new BicicletaStatus();

        status.setHandler_rotation(360);
        status.setHandler_hardness(20);

        status.setWheel_1_Y(20);
        status.setWheel_1_X(-25);
        status.setWheel_1_Z(-50);

        status.setWheel_2_Y(20);
        status.setWheel_2_X(-25);
        status.setWheel_2_Z(50);

        status.setWheel_1_break(80);
        status.setWheel_2_break(80);

        return status;
    }
    private Set<String> makeBiciConFallos() {
        Set<String> fallos = new HashSet<>();
        fallos.add("HAN_HAR");
        fallos.add("WHE_VIB");
        return fallos;
    }
}
