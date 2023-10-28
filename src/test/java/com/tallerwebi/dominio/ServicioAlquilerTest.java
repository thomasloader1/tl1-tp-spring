package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.servicios.ServicioAlquilerImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquiler;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
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
        servicioAlquiler = new ServicioAlquilerImpl(repositorioAlquilerMock,repositorioBicicletaMock);
    }

    @Test
    public void queSePuedaCrearUnAlquiler() throws AlquilerValidacion {
        // preparación
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(mock(Bicicleta.class));
        when(datosAlquilerMock.getUsuario()).thenReturn(mock(Usuario.class));
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);

        // ejecución
        servicioAlquiler.crearAlquiler(datosAlquilerMock);

        // validación
        verify(repositorioAlquilerMock, times(1)).crearAlquiler(any(Alquiler.class));
    }

  //  @Test
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
//        verify(repositorioAlquilerMock, times(1)).eliminarAlquiler(alquilerMock);
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
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(1);

        // ejecución
        servicioAlquiler.crearAlquiler(datosAlquilerMock);
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
        assertThrows(AlquilerValidacion.class, () -> servicioAlquiler.crearAlquiler(datosAlquilerMock));
        verify(repositorioAlquilerMock, times(0)).crearAlquiler(any(Alquiler.class));
    }

    @Test
    public void queSePuedaCalcularElPrecioDelAlquiler() {
        // Crea un objeto de datos de alquiler con valores de ejemplo
        DatosAlquiler datosAlquilerMock = mock(DatosAlquiler.class);
        when(datosAlquilerMock.getBicicleta()).thenReturn(mock(Bicicleta.class));
        when(datosAlquilerMock.getPrecioxhora()).thenReturn(500.0);
        when(datosAlquilerMock.getCantidadHoras()).thenReturn(2);
        when(datosAlquilerMock.getBicicleta().getCondicion()).thenReturn(Condition.BUENO_ESTADO);

        // Llama al método que quieres probar
        double precioFinal = servicioAlquiler.calcularPrecioAlquiler(datosAlquilerMock);

        // Verifica si el precio final calculado es el esperado
        // En este caso, esperamos que el precio sea 500.0 (precio base) * 2 (horas) * 0.8 (buen estado) = 800.0
        assertEquals(800.0, precioFinal, 0.01); // El tercer argumento es la tolerancia para la comparación de valores flotantes

    }

}
