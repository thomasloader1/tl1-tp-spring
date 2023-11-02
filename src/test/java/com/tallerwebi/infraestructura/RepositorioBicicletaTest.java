package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicletaImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RepositorioBicicletaTest {
    private SessionFactory sessionFactory;
    private Session sessionMock;
    private RepositorioBicicletaImpl repositorioBicicleta;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioBicicleta = new RepositorioBicicletaImpl(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(sessionMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaRegistrarUnaBicicleta() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(bicicletaMock.getId()).thenReturn(1L);
        when(sessionMock.get(Bicicleta.class, bicicletaMock.getId())).thenReturn(bicicletaMock);

        // ejecución
        repositorioBicicleta.registrarBicicleta(bicicletaMock);

        // validación
        verify(sessionMock, times(1)).save(bicicletaMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEliminarUnaBicicleta() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(bicicletaMock.getId()).thenReturn(1L);
        when(sessionMock.get(Bicicleta.class, bicicletaMock.getId())).thenReturn(bicicletaMock);

        // ejecución
        repositorioBicicleta.registrarBicicleta(bicicletaMock);
        repositorioBicicleta.eliminarBicicleta(bicicletaMock);

        // validación
        verify(sessionMock, times(1)).delete(bicicletaMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaBicicletaPorId() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        when(bicicletaMock.getId()).thenReturn(1L);
        when(sessionMock.get(Bicicleta.class, bicicletaMock.getId())).thenReturn(bicicletaMock);

        // ejecución
        repositorioBicicleta.registrarBicicleta(bicicletaMock);
        Bicicleta bicicleta = repositorioBicicleta.obtenerBicicletaPorId(bicicletaMock.getId());

        // validación
        verify(sessionMock, times(1)).get(Bicicleta.class, bicicletaMock.getId());
        assertTrue(bicicleta != null);
        assertEquals(bicicletaMock, bicicleta);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeLasBicicletasDelUsuario() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(List.of());

        // ejecución
        List<Bicicleta> bicicletas = repositorioBicicleta.obtenerBicicletasDelUsuario(usuarioMock);

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("SELECT b FROM Bicicleta b WHERE b.usuario = :usuario");
        verify(queryMock).setParameter("usuario", usuarioMock);
        verify(queryMock).list();
        assertEquals(0, bicicletas.size());
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletasDisponibles() {
        // preparación
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(List.of());

        // ejecución
        List<Bicicleta> bicicletas = repositorioBicicleta.obtenerBicicletasDisponibles();

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("SELECT b FROM Bicicleta b WHERE b.estadoBicicleta = :estado");
        verify(queryMock).list();
        assertEquals(0, bicicletas.size());
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeTodasLasBicicletasDisponiblesDeUnPropietario() {
        // preparación
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);

        Usuario propietarioMock = mock(Usuario.class);

        Bicicleta bicicleta1 = mock(Bicicleta.class);
        when(bicicleta1.getUsuario()).thenReturn(propietarioMock);
        when(bicicleta1.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);

        Bicicleta bicicleta2 = mock(Bicicleta.class);
        when(bicicleta2.getUsuario()).thenReturn(propietarioMock);
        when(bicicleta2.getEstadoBicicleta()).thenReturn(EstadoBicicleta.DISPONIBLE);

        List<Bicicleta> bicicletasMock = new ArrayList<>();
        bicicletasMock.add(bicicleta1);
        bicicletasMock.add(bicicleta2);

        when(repositorioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(propietarioMock.getId())).thenReturn(bicicletasMock);

        // ejecución
        List<Bicicleta> bicicletas = repositorioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(propietarioMock.getId());

        // validación
        assertEquals(2, bicicletas.size());
        assertEquals(bicicleta1, bicicletas.get(0));
        assertEquals(bicicleta2, bicicletas.get(1));
    }

}