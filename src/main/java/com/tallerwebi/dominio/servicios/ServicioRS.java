package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.BicicletaStatus;
import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.presentacion.dto.Rueda;

public interface ServicioRS {

    float calculateHandlerRotationRS(float handlerRotation);

    float calculateHAndlerHardnessRS(float handlerHardness);

    float calculateWheelMovementRS(Rueda rueda);

    float calculateBreakStrengRS(float wheel1Break);

    float calculateGeneralRS(BicicletaStatus status);

    Condition analizeCondition(float score);

    void updateBicicondition(Bicicleta bicicleta, float score);
}
