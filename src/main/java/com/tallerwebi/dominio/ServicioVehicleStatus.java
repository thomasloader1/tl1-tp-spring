package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.BicicletaStatus;

import java.util.Set;

public interface ServicioVehicleStatus {
    boolean checkWheelVibration(BicicletaStatus status);

    boolean checkWheelBreaks(BicicletaStatus status);

    boolean checkHandlerRotation(BicicletaStatus status);

    boolean checkHandlerHardness(BicicletaStatus status);

    Set<String> checkGeneralStatus(BicicletaStatus status);
}
