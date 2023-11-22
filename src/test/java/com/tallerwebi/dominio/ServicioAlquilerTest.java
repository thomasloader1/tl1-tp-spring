package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.servicios.ServicioAlquilerImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquiler;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ServicioAlquilerTest {
    private ServicioAlquilerImpl servicioAlquiler;
    private RepositorioAlquiler repositorioAlquilerMock;
    private RepositorioBicicleta repositorioBicicletaMock;

    @BeforeEach
    public void init() {
        repositorioAlquilerMock = mock(RepositorioAlquiler.class);
        repositorioBicicletaMock = mock(RepositorioBicicleta.class);
        servicioAlquiler = new ServicioAlquilerImpl(repositorioAlquilerMock, repositorioBicicletaMock);
    }

    @Test
    public void queSePuedaCrearUnAlquiler() throws AlquilerValidacion {
        // preparación

        Alquiler alquilerMock = mock(Alquiler.class);
        when(alquilerMock.getBicicleta()).thenReturn(mock(Bicicleta.class));
        when(alquilerMock.getUsuario()).thenReturn(mock(Usuario.class));
        when(alquilerMock.getBicicleta().getCondicion()).thenReturn(Condition.PERFECTO_ESTADO);
        when(alquilerMock.getCantidadHoras()).thenReturn(1);

        // ejecución
        servicioAlquiler.crearAlquiler(alquilerMock);
        // validación
        verify(repositorioAlquilerMock, times(1)).crearAlquiler(any(Alquiler.class));
    }

//    @Test
//    public void queSePuedaFinalizarUnAlquilerYLoElimine() {
//        // preparación
//        Alquiler alquilerMock = mock(Alquiler.class);
//        when(repositorioAlquilerMock.obtenerAlquilerporId(anyLong())).thenReturn(alquilerMock);
//        when(alquilerMock.getEstadoAlquiler()).thenReturn(EstadoBicicleta.EN_USO);
//
//        // ejecución
//        servicioAlquiler.finalizarAlquiler(alquilerMock.getId());
//
//        // validación
//        when(alquilerMock.getEstadoAlquiler()).thenReturn(EstadoBicicleta.DISPONIBLE);
//        assertEquals(EstadoBicicleta.DISPONIBLE, alquilerMock.getEstadoAlquiler());
//    }

    @Test
    public void queSePuedaObtenerUnaBicicletaPorIdDeAlquiler() {
        // preparación
        Alquiler alquilerMock = mock(Alquiler.class);
        when(repositorioAlquilerMock.obtenerAlquilerporId(anyLong())).thenReturn(alquilerMock);
        when(alquilerMock.getBicicleta()).thenReturn(mock(Bicicleta.class));

        // ejecución
        Bicicleta bicicleta = servicioAlquiler.obtenerBicicletaPorIdDeAlquiler(alquilerMock.getId());

        // validación
        verify(repositorioAlquilerMock, times(1)).obtenerAlquilerporId(anyLong());
        assertEquals(alquilerMock.getBicicleta(), bicicleta);
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodosLosAlquileresDeUnaBicicleta() throws AlquilerValidacion {
        // preparación
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Usuario usuarioMock = mock(Usuario.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosAlquilerMock.getUsuario()).thenReturn(usuarioMock);
        when(datosAlquilerMock.getBicicleta().getCondicion()).thenReturn(Condition.PERFECTO_ESTADO);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);
        Alquiler alquilerMock = new Alquiler(datosAlquilerMock.getCantidadHoras(), datosAlquilerMock.getBicicleta(), datosAlquilerMock.getUsuario());

        // ejecución
        servicioAlquiler.crearAlquiler(alquilerMock);
        when(repositorioAlquilerMock.obtenerTodosLosAlquileresDeUnaBicicleta(bicicletaMock)).thenReturn(List.of(new Alquiler(1, bicicletaMock, usuarioMock)));
        List<Alquiler> alquileres = servicioAlquiler.obtenerTodosLosAlquileresDeUnaBicicleta(datosAlquilerMock);

        // validación
        verify(repositorioAlquilerMock, times(1)).obtenerTodosLosAlquileresDeUnaBicicleta(bicicletaMock);
        assertEquals(1, alquileres.size());
        assertEquals(datosAlquilerMock.getBicicleta(), alquileres.get(0).getBicicleta());
    }

    @Test
    public void queLanceUnaExcepcionSiLaCantidadDeHorasDeAlquilerEsMenorAUnoYNoSePuedeCrearElAlquiler() {
        // preparación
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);

        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Usuario usuarioMock = mock(Usuario.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosAlquilerMock.getUsuario()).thenReturn(usuarioMock);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(0);

        // ejecución y validación
        assertThrows(AlquilerValidacion.class, () -> servicioAlquiler.comenzarAlquiler(datosAlquilerMock));
        verify(repositorioAlquilerMock, times(0)).crearAlquiler(any(Alquiler.class));
    }
}
