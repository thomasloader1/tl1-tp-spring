package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.BicicletaStatus;

import java.util.HashSet;
import java.util.Set;

enum Fallos {
    HAN_HAR, HAN_ROT, WHE_VIB, WHE_BRE,
}

public class ServicioVehicleStatusIMP implements ServicioVehicleStatus {

    @Override
    public boolean checkWheelVibration(BicicletaStatus status) {
        final float TOL = 50;
        return Math.abs(status.getWheel_1_X()) <= TOL && Math.abs(status.getWheel_1_Y()) <= TOL && Math.abs(status.getWheel_1_Z()) <= TOL && Math.abs(status.getWheel_2_X()) <= TOL && Math.abs(status.getWheel_2_Y()) <= TOL && Math.abs(status.getWheel_2_Z()) <= TOL;
    }

    @Override
    public boolean checkWheelBreaks(BicicletaStatus status) {
        final float TOL = 80;
        return status.getWheel_1_break() >= TOL && status.getWheel_2_break() >= TOL;
    }

    @Override
    public boolean checkHandlerRotation(BicicletaStatus status) {
        final float ROT = 360;
        return status.getHandler_rotation() >= ROT;
    }

    @Override
    public boolean checkHandlerHardness(BicicletaStatus status) {
        final float TOL = 30;
        return status.getHandler_hardness() <= TOL;
    }

    @Override
    public Set<String> checkGeneralStatus(BicicletaStatus status) {
        Set<String> fallos = new HashSet<>();

        if (!checkWheelVibration(status)) {
            fallos.add(String.valueOf(Fallos.WHE_VIB));
        }

        if (!checkWheelBreaks(status)) {
            fallos.add(String.valueOf(Fallos.WHE_BRE));
        }

        if (!checkHandlerRotation(status)) {
            fallos.add(String.valueOf(Fallos.HAN_ROT));
        }

        if (!checkHandlerHardness(status)) {
            fallos.add(String.valueOf(Fallos.HAN_HAR));
        }

        return fallos;
    }
}
