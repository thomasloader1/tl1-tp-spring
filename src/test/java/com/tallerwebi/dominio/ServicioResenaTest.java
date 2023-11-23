package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.dominio.servicios.ServicioResenaImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.infraestructura.repositorios.RepositorioResena;
import com.tallerwebi.presentacion.dto.DatosResena;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioResenaTest {
    private ServicioResenaImpl servicioResena;
    private RepositorioResena repositorioResenaMock;
    private Bicicleta bicicletaMock;
    private RepositorioBicicleta repositorioBicicletaMock;

    @BeforeEach
    public void init() {
        repositorioResenaMock = mock(RepositorioResena.class);
        repositorioBicicletaMock = mock(RepositorioBicicleta.class);
        servicioResena = new ServicioResenaImpl(repositorioResenaMock, repositorioBicicletaMock);
        bicicletaMock = mock(Bicicleta.class);
    }

    @Test
    public void queSePuedaCrearUnaResenaDeUnaBicicleta() throws ResenaValidacion, ResenaPuntajeValidacion {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(datosResenaMock.getComentario()).thenReturn("Comentario");
        when(datosResenaMock.getPuntaje()).thenReturn(5);
        when(datosResenaMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosResenaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución
        servicioResena.subirResena(datosResenaMock);

        // validación
        verify(repositorioResenaMock, times(1)).subirResena(any(Resena.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElComentarioDeLaResenaDeUnaBicicletaEsVacioYNoSePuedeCrearLaResena() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(datosResenaMock.getComentario()).thenReturn("");
        when(datosResenaMock.getPuntaje()).thenReturn(5);
        when(datosResenaMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosResenaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(ResenaValidacion.class, () -> servicioResena.subirResena(datosResenaMock));
        verify(repositorioResenaMock, times(0)).subirResena(any(Resena.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElPuntajeDeLaResenaDeUnaBicicletaEsNuloYNoSePuedeCrearLaResena() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(datosResenaMock.getComentario()).thenReturn("Comentario");
        when(datosResenaMock.getPuntaje()).thenReturn(null);
        when(datosResenaMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosResenaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(ResenaValidacion.class, () -> servicioResena.subirResena(datosResenaMock));
        verify(repositorioResenaMock, times(0)).subirResena(any(Resena.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElPuntajeDeLaResenaDeUnaBicicletaEsMenorAUnoYNoSePuedeCrearLaResena() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(datosResenaMock.getComentario()).thenReturn("Comentario");
        when(datosResenaMock.getPuntaje()).thenReturn(0);
        when(datosResenaMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosResenaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(ResenaPuntajeValidacion.class, () -> servicioResena.subirResena(datosResenaMock));
        verify(repositorioResenaMock, times(0)).subirResena(any(Resena.class));
    }

    @Test
    public void queLanceUnaExcepcionSiElPuntajeDeLaResenaDeUnaBicicletaEsMayorACincoYNoSePuedeCrearLaResena() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        DatosResena datosResenaMock = mock(DatosResena.class);
        when(datosResenaMock.getComentario()).thenReturn("Comentario");
        when(datosResenaMock.getPuntaje()).thenReturn(6);
        when(datosResenaMock.getBicicleta()).thenReturn(bicicletaMock);
        when(datosResenaMock.getUsuario()).thenReturn(usuarioMock);

        // ejecución y validación
        assertThrows(ResenaPuntajeValidacion.class, () -> servicioResena.subirResena(datosResenaMock));
        verify(repositorioResenaMock, times(0)).subirResena(any(Resena.class));
    }

    @Test
    public void queSePuedaObtenerUnaListaDeTodasLasResenasDeUnaBicicleta() {
        // preparación
        Resena resenaMock = mock(Resena.class);
        when(repositorioResenaMock.obtenerResenasDeUnaBicicleta(bicicletaMock)).thenReturn(List.of(resenaMock));

        // ejecución
        List<Resena> resenas = servicioResena.obtenerResenasDeUnaBicicleta(bicicletaMock);

        // validación
        verify(repositorioResenaMock, times(1)).obtenerResenasDeUnaBicicleta(any(Bicicleta.class));
        assertEquals(1, resenas.size());
        assertEquals(resenaMock, resenas.get(0));
    }
    @Test
    public void queSePuedaObtenerUnaListaDeLasResenasDeUnCliente(){
        //preparacion
        Resena resenaMock = mock(Resena.class);
        Resena resenaMock2 = mock(Resena.class);
        Usuario usuarioMock = mock(Usuario.class);
        when(repositorioResenaMock.obtenerResenasDeUnaClientePorId(usuarioMock.getId())).thenReturn(List.of(resenaMock, resenaMock2));

        // ejecucion
        List <Resena> resenasPorId = servicioResena.obtenerResenasDeUnaClientePorId(usuarioMock.getId());
        // validacion
        verify(repositorioResenaMock ,times(1)).obtenerResenasDeUnaClientePorId(usuarioMock.getId());
        assertEquals(2,resenasPorId.size());
        assertEquals(resenaMock, resenasPorId.get(0));
    }
    @Test
    public void queSePuedaObtenerUnaListaDeLasResenasIgualesACincoEnPuntajeDeUnCliente(){
        //preparacion
        Resena resenaMock = mock(Resena.class);
        resenaMock.setPuntaje(5);
        Resena resenaMock2 = mock(Resena.class);
        resenaMock2.setPuntaje(5);
        Usuario usuarioMock = mock(Usuario.class);
        when(repositorioResenaMock.obtenerResenasDeUnaClientePorIdPuntajeBueno(usuarioMock.getId())).thenReturn(List.of(resenaMock, resenaMock2));

        // ejecucion
        List <Resena> resenasPorId = servicioResena.obtenerResenasDeUnaClientePorIdPuntajeBueno(usuarioMock.getId());
        // validacion
        verify(repositorioResenaMock ,times(1)).obtenerResenasDeUnaClientePorIdPuntajeBueno(usuarioMock.getId());
        assertEquals(2,resenasPorId.size());
        assertEquals(resenaMock, resenasPorId.get(0));
    }
}
