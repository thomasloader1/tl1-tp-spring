package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.BicicletaStatus;
import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.dominio.servicios.ServicioRS;
import com.tallerwebi.dominio.servicios.ServicioRSImp;
import com.tallerwebi.presentacion.dto.Rueda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioRSTest {
    private static final float PERFECT_SCORE = 100.0F;
    private ServicioRS servicioRS;

    @BeforeEach
    public void init() {
        servicioRS = new ServicioRSImp();
    }

    @Test
    public void rotacionManillarA360Da100RS() {
        BicicletaStatus status = makeStatus();

        float score = servicioRS.calculateHandlerRotationRS(status.getHandler_rotation());

        assertEquals(score, PERFECT_SCORE);
    }

    @Test
    public void durezaManillarEnPErfectoEstadoDa100RS() {
        BicicletaStatus status = makeStatus();

        float score = servicioRS.calculateHAndlerHardnessRS(status.getHandler_hardness());

        assertEquals(score, PERFECT_SCORE);
    }

    @Test
    public void movimientoDeRuedasEnPerfectoEstadoDa100RS() {
        BicicletaStatus status = makeStatus();
        Rueda rueda_1 = new Rueda(status.getWheel_1_X(), status.getWheel_1_Y(), status.getWheel_1_Z());
        Rueda rueda_2 = new Rueda(status.getWheel_2_X(), status.getWheel_2_Y(), status.getWheel_2_Z());

        float score_1 = servicioRS.calculateWheelMovementRS(rueda_1);
        float score_2 = servicioRS.calculateWheelMovementRS(rueda_2);

        assertEquals(score_1, PERFECT_SCORE);
        assertEquals(score_2, PERFECT_SCORE);
    }

    @Test
    public void fuerzaDeFrenosEnPerfectoEstadoDa100RS() {
        BicicletaStatus status = makeStatus();

        float score_1 = servicioRS.calculateBreakStrengRS(status.getWheel_1_break());
        float score_2 = servicioRS.calculateBreakStrengRS(status.getWheel_2_break());

        assertEquals(score_1, PERFECT_SCORE);
        assertEquals(score_2, PERFECT_SCORE);
    }

    @Test
    public void todoEnEstadoPerfectoDa100RSGral(){
        BicicletaStatus status = makeStatus();

        float gral_score = servicioRS.calculateGeneralRS(status);

        assertEquals(gral_score, PERFECT_SCORE);
    }

    @Test
    public void rotacionEnEstadoNoPerfectoNoDa100RS(){
        final float EX_SCORE = 36.11F;
        BicicletaStatus status = makeStatus();
        status.setHandler_rotation(130F);

        float score = servicioRS.calculateHandlerRotationRS(status.getHandler_rotation());

        assertTrue(score < PERFECT_SCORE);
        assertEquals(score, EX_SCORE);
    }

    @Test
    public void durezaManillarEnEstadoNoPerfectoNoDa100RS(){
        final float EX_SCORE = 50.0F;
        BicicletaStatus status = makeStatus();
        status.setHandler_hardness(10);

        float score = servicioRS.calculateHAndlerHardnessRS(status.getHandler_hardness());

        assertTrue(score < PERFECT_SCORE);
        assertEquals(score, EX_SCORE);
    }

    @Test
    public void movimientoRuetaEnEstadoNoPerfectoNoDa100RS(){
        final float EX_SCORE_1 = 93.33F;
        final float EX_SCORE_2 = 80.0F;
        BicicletaStatus status = makeStatus();
        status.setWheel_1_X(30);
        status.setWheel_2_X(10);

        Rueda rueda_1 = new Rueda(status.getWheel_1_X(), status.getWheel_1_Y(), status.getWheel_1_Z());
        Rueda rueda_2 = new Rueda(status.getWheel_2_X(), status.getWheel_2_Y(), status.getWheel_2_Z());

        float score_1 = servicioRS.calculateWheelMovementRS(rueda_1);
        float score_2 = servicioRS.calculateWheelMovementRS(rueda_2);

        assertTrue(score_1 < PERFECT_SCORE);
        assertEquals(score_1, EX_SCORE_1);

        assertTrue(score_2 < PERFECT_SCORE);
        assertEquals(score_2, EX_SCORE_2);
    }

    @Test
    public void fuerzaFrenosEnEstadoNoPErfectoNoDa100RS(){
        final float EX_SCORE_1 = 80.0F;
        final float EX_SCORE_2 = 50.0F;
        BicicletaStatus status = makeStatus();
        status.setWheel_1_break(80);
        status.setWheel_2_break(50);

        float score_1 = servicioRS.calculateBreakStrengRS(status.getWheel_1_break());
        float score_2 = servicioRS.calculateBreakStrengRS(status.getWheel_2_break());

        assertTrue(score_1 < PERFECT_SCORE);
        assertEquals(score_1, EX_SCORE_1);

        assertTrue(score_2 < PERFECT_SCORE);
        assertEquals(score_2, EX_SCORE_2);
    }

    @Test
    public void conAlgoQueNoEsteenEstadoPerefctoNoDa100RSGral(){
        final float EX_SCORE = 97.5F;
        BicicletaStatus status = makeStatus();
        status.setWheel_1_break(80);

        float gral_score = servicioRS.calculateGeneralRS(status);

        assertTrue(gral_score < PERFECT_SCORE);
        assertEquals(gral_score, EX_SCORE);
    }

    @Test
    public void unaCondicionMayorA70RSDevuelveEnPerfectoEstado(){
        Condition EX_COND = Condition.PERFECTO_ESTADO;
        float score = 75F;

        Condition condition = servicioRS.analizeCondition(score);

        assertEquals(condition, EX_COND);
    }

    @Test
    public  void unaCondicionMenorA70yMayotA40DevuelveEnBuenEstado(){
        Condition EX_COND = Condition.BUENO_ESTADO;
        float score_1 = 50F;
        float score_2 = 70F;
        float score_3 = 10F;

        Condition condition_1 = servicioRS.analizeCondition(score_1);
        Condition condition_2 = servicioRS.analizeCondition(score_2);
        Condition condition_3 = servicioRS.analizeCondition(score_3);

        assertEquals(condition_1, EX_COND);
        assertNotEquals(condition_2, EX_COND);
        assertNotEquals(condition_3, EX_COND);
    }

    @Test
    public  void unaCondicionMenorA40DevuelveMalEstaod(){
        Condition EX_COND = Condition.MAL_ESTADO;
        float score = 10F;

        Condition condition = servicioRS.analizeCondition(score);

        assertEquals(condition, EX_COND);
    }

    @Test
    public void actualizaLaCondicionDeUnaBicicletaQueNoEstaenPErfectoEstado(){
        Condition EX_COND = Condition.BUENO_ESTADO;
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setCondicion(Condition.PERFECTO_ESTADO);
        float score = 50F;

        servicioRS.updateBicicondition(bicicleta, score);

        assertEquals(bicicleta.getCondicion(), EX_COND);
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

        status.setWheel_1_break(100);
        status.setWheel_2_break(100);

        return status;
    }
}
