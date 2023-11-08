package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.dominio.servicios.*;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicletaImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioResena;
import com.tallerwebi.infraestructura.repositorios.RepositorioVehicleStatus;
import com.tallerwebi.presentacion.dto.DatosResena;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioResenaTest {
    private ServicioResenaImpl servicioResena;
    private RepositorioResena repositorioResenaMock;
    private Bicicleta bicicletaMock;
    private ServicioBicicleta servicioBicicleta;

    @BeforeEach
    public void init() {
        repositorioResenaMock = mock(RepositorioResena.class);
        servicioResena = new ServicioResenaImpl(repositorioResenaMock);
        bicicletaMock = mock(Bicicleta.class);
        servicioBicicleta = new ServicioBicicletaImpl(mock(RepositorioBicicletaImpl.class), mock(RepositorioVehicleStatus.class), new ServicioVehicleStatusIMP(), repositorioResenaMock);

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
    public void queCuandoSeSubaUnaResenaSeIncrementeElPuntajeTotalEnLaBici() throws ResenaValidacion, ResenaPuntajeValidacion {
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setPuntaje(10);

        servicioResena.subirResena(new DatosResena(5, "Comentario",bicicleta,new Usuario()));

        assertEquals(15, bicicleta.getPuntaje());
    }

    @Test
    public void queSeACtualiceCorrectamenteElNuevoPuntajeCuandoSeGuardanVariasResenas() throws ResenaValidacion, ResenaPuntajeValidacion {
        final int ESPERADO_1 = 4;
        final int ESPERADO_2 = 3;
        final int ESPERADO_3 = 4;
        final int CANT_RESENAS_INICIAL = 7;

        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setPuntaje(26);
        Answer<Integer> mockAnswer = CrearNuevaRespuestaMock(CANT_RESENAS_INICIAL);
        when(repositorioResenaMock.obtenerCantidadDeResenasParaUnaBicicleta(any())).thenAnswer(mockAnswer);

        DatosResena datosResena = new DatosResena(4,"Comentario",bicicleta, new Usuario());

        servicioResena.subirResena(datosResena);
        int puntajeObtenido_1 = servicioResena.calcularPuntaje(bicicleta);

        datosResena.setPuntaje(1);
        servicioResena.subirResena(datosResena);
        int puntajeObtenido_2 = servicioResena.calcularPuntaje(bicicleta);

        datosResena.setPuntaje(5);
        servicioResena.subirResena(datosResena);
        int puntajeObtenido_3 = servicioResena.calcularPuntaje(bicicleta);

        assertEquals(ESPERADO_1, puntajeObtenido_1);
        assertEquals(ESPERADO_2, puntajeObtenido_2);
        assertEquals(ESPERADO_3, puntajeObtenido_3);
    }

    /* Creo atente contra el tratado de Ginebra haciendo esto ☻*/
    /* Sirve para mockear un valor dinamico*/
    private Answer<Integer> CrearNuevaRespuestaMock(int valorInicial) {
        return new Answer<Integer>() {
            private int valorDinamico = valorInicial;

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return this.valorDinamico++;
            }
        };
    }
}
