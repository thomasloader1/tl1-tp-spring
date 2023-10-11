package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.BicicletaStatus;
import com.tallerwebi.dominio.servicios.ServicioRS;
import com.tallerwebi.dominio.servicios.ServicioRSImp;
import com.tallerwebi.infraestructura.repositorios.RepositorioVehicleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioRSTest {
    RepositorioVehicleStatus repositorioStado;
    private ServicioRS servicioRS;

    @BeforeEach
    public void init(){
        repositorioStado = mock(RepositorioVehicleStatus.class);
        servicioRS = new ServicioRSImp();
    }

    @Test
    public void siEstaTodoOkRSEs100(){
        when(repositorioStado.getStatus(anyLong())).thenReturn(makeStatus());

        BicicletaStatus status = repositorioStado.getStatus(1L);

        Integer score = servicioRS.calculateScore(status);

       assertTrue(100 == score);
    }

    @Test
    public  void siEstaTodoMalREEs0(){
        when(repositorioStado.getStatus(anyLong())).thenReturn(makeStatus());

        //Quieor analizar estado
        BicicletaStatus status = repositorioStado.getStatus(1L);
        status.setWheel_1_break(0);

        Integer score = servicioRS.calculateScore(status);

        assertTrue(score == 0);

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
}
