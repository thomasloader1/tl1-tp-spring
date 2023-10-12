package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.BicicletaStatus;
import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.presentacion.dto.Rueda;

public class ServicioRSImp implements ServicioRS {
    private static final Float CIEN_PORCIENTO = 100F;
    private static final Integer CANT_EVALUACIONES = 4;

    public ServicioRSImp() {
    }

    @Override
    public float calculateHandlerRotationRS(float handlerRotation) {
        final float perfectStatus = 360F;
        return this.floatTo2Decimal(this.calculatePerformance(perfectStatus, handlerRotation));
    }

    @Override
    public float calculateHAndlerHardnessRS(float handlerHardness) {
        final float perfectStatus = 20;
        return this.floatTo2Decimal(this.calculatePerformance(perfectStatus, handlerHardness));
    }

    @Override
    public float calculateWheelMovementRS(Rueda rueda) {
        final float perfect_X = 25.0F;
        final float perfect_Y = 20.0F;
        final float perfect_Z = 50.0F;
        final float CANT_PARAMETERS = 3;

        float score_X = calculatePerformance(perfect_X, Math.abs(rueda.getWheel_x()));
        float score_Y = calculatePerformance(perfect_Y, Math.abs(rueda.getWheel_y()));
        float score_Z = calculatePerformance(perfect_Z, Math.abs(rueda.getWheel_z()));

        return this.floatTo2Decimal((score_X + score_Y + score_Z) / CANT_PARAMETERS);
    }

    @Override
    public float calculateBreakStrengRS(float wheelBreak) {
        final float perfectStatus = 100.0F;
        return this.floatTo2Decimal(this.calculatePerformance(perfectStatus, wheelBreak));
    }

    @Override
    public float calculateGeneralRS(BicicletaStatus status) {
        float score_hand_rot = this.calculateHandlerRotationRS(status.getHandler_rotation());
        float score_hand_hard = this.calculateHAndlerHardnessRS(status.getHandler_hardness());

        float score_wheel_1 = this.calculateWheelMovementRS(new Rueda(status.getWheel_1_X(), status.getWheel_1_Y(), status.getWheel_1_Z()));
        float score_wheel_2 = this.calculateWheelMovementRS(new Rueda(status.getWheel_2_X(), status.getWheel_2_Y(), status.getWheel_2_Z()));
        float score_wheels = (score_wheel_1 + score_wheel_2) / 2;

        float score_break_1 = this.calculateBreakStrengRS(status.getWheel_1_break());
        float score_break_2 = this.calculateBreakStrengRS(status.getWheel_2_break());
        float score_breaks = (score_break_1 + score_break_2) / 2;

        return this.floatTo2Decimal((score_hand_rot + score_hand_hard + score_wheels + score_breaks) / CANT_EVALUACIONES);
    }

    @Override
    public Condition analizeCondition(float score) {
        Condition condition = null;

        if(score >= 70F){
            condition = Condition.PERFECTO_ESTADO;
        }else {
            if(score >= 40 && score < 70){
                condition = Condition.BUENO_ESTADO;
            }else{
                condition = Condition.MAL_ESTADO;
            }
        }

        return condition;
    }

    @Override
    public void updateBicicondition(Bicicleta bicicleta, float score) {
        bicicleta.setCondicion(this.analizeCondition(score));
    }

    private float tresSimple(Float valor_I, Float valor_D, Float buscado) {
        return (buscado * valor_D) / valor_I;
    }

    private float calculatePerformance(float perfectValue, float input) {
        float output = CIEN_PORCIENTO - (this.tresSimple(perfectValue, CIEN_PORCIENTO, Math.abs(perfectValue - input)));
        return output < 0 ? 0 : output > 100 ? 100 : output;
    }

    private float floatTo2Decimal(float number) {
        return Math.round(number * 100.0F) / 100.0F;
    }
}
