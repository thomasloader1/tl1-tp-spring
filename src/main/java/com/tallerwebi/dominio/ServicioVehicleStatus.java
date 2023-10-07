package com.tallerwebi.dominio;

import java.util.Set;

public interface ServicioVehicleStatus {
    boolean checkWheelVibration(StatusDAO status);

    boolean checkWheelBreaks(StatusDAO status);

    boolean checkHandlerRotation(StatusDAO status);

    boolean checkHandlerHardness(StatusDAO status);

    Set<String> checkGeneralStatus(StatusDAO status);
}
