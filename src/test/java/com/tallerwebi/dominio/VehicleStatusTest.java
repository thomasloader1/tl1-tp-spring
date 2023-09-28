package com.tallerwebi.dominio;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tallerwebi.infraestructura.RepositorioVehicleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class VehicleStatusTest {

    private ServicioVehicleStatus servicioVehicleStatus;
    private RepositorioVehicleStatus vehicleStatusRepositorio;

    @BeforeEach
    public void init() {
        servicioVehicleStatus = new ServicioVehicleStatusIMP();
        vehicleStatusRepositorio = mock(RepositorioVehicleStatus.class);
        when(vehicleStatusRepositorio.getData(any())).thenReturn(makeStatus());
    }

    @Test
    public void unVehiculoEsOperativoSiCumpleConTodosLosCheckeos(){
        StatusDAO status = vehicleStatusRepositorio.getData("V_001");
        assertTrue(status != null);
        assertTrue(servicioVehicleStatus.checkWheelVibration(status));
        assertTrue(servicioVehicleStatus.checkWheelBreaks(status));
        assertTrue(servicioVehicleStatus.checkHandlerRotation(status));
        assertTrue(servicioVehicleStatus.checkHandlerHardness(status));
    }

    @Test
    public void siAlgunaRuedaTieneMovimientoExcedenteALaToleranciaNoEsOperativo() {
        StatusDAO status = vehicleStatusRepositorio.getData("V_001");
        status.setWheel_1_Y(80);
        assertTrue(status != null);
        assertFalse(servicioVehicleStatus.checkWheelVibration(status));
    }

    @Test
    public void siLaFuerzaDeLosFrenosEsMenorALaToleranciaNoEsOperativo() {
        StatusDAO status = vehicleStatusRepositorio.getData("V_001");
        status.setWheel_2_break(20);
        assertTrue(status != null);
        assertFalse(servicioVehicleStatus.checkWheelBreaks(status));
    }

    @Test
    public void siLaRotacionDelManillarNoGira360gNoEsOperativo() {
        StatusDAO status = vehicleStatusRepositorio.getData("V_001");
        status.setHandler_rotation(220);
        assertTrue(status != null);
        assertFalse(servicioVehicleStatus.checkHandlerRotation(status));
    }

    @Test
    public void siLaDurezaDelManilalrSuperaLaToleranciaNoEsOperativo() {
        StatusDAO status = vehicleStatusRepositorio.getData("V_001");
        status.setHandler_hardness(100);
        assertTrue(status != null);
        assertFalse(servicioVehicleStatus.checkHandlerHardness(status));
    }

    @Test
    public void elEstadoGeneraUnaListaDeFallas(){
        StatusDAO status = vehicleStatusRepositorio.getData("V_001");
        Set<String> fallosEsperados = makeSetDeFallos();
        status.setHandler_hardness(100);
        status.setWheel_1_Y(80);
        Set<String> fallos = servicioVehicleStatus.checkGeneralStatus(status);
        assertTrue(fallos.size() == 2);
        assertTrue(fallos.equals(fallosEsperados));
    }

    private Set<String> makeSetDeFallos() {
        Set<String> fallos = new HashSet<>();
        fallos.add("HAN_HAR");
        fallos.add("WHE_VIB");
        return fallos;
    }

    //Creacion de un estado valido
    private StatusDAO makeStatus() {
        StatusDAO status = new StatusDAO();

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

}