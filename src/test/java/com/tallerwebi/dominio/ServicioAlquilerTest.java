package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import com.tallerwebi.dominio.servicios.ServicioAlquilerImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquilerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ServicioAlquilerTest {
    @Mock
    private RepositorioAlquilerImpl repositorioAlquiler;

    @InjectMocks
    private ServicioAlquilerImpl servicioAlquiler;

    private Alquiler alquiler;
    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

  /*  @Test
    void finalizarUnAlquiler(){
        Alquiler alquiler = new Alquiler();
        alquiler.setId(new Long(1));
        alquiler.setEstadoAlquiler(EstadoAlquiler.EN_CURSO);

        Mockito.when(repositorioAlquiler.obtenerAlquilerporId(Mockito.any(Long.class))).thenReturn(alquiler);

       Alquiler alquilerEsperado = repositorioAlquiler.obtenerAlquilerporId(1L);
       Assertions.assertNotNull(alquilerEsperado);

       Mockito.when(servicioAlquiler.finalizarAlquiler(Mockito.any(Long.class))).thenReturn(alquilerEsperado);


       Assertions.assertEquals(EstadoAlquiler.FINALIZADO, alquilerEsperado.getEstadoAlquiler());
    }*/
}
