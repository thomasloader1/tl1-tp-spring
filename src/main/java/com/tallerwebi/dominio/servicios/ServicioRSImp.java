package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.BicicletaStatus;

public class ServicioRSImp implements ServicioRS{
    public ServicioRSImp() {
    }

    @Override
    public Integer calculateScore(BicicletaStatus l) {
        int resultado = 0;

        if(l.getWheel_1_break() == 80){
            resultado = 100;
        }

        return resultado;
    }
}
