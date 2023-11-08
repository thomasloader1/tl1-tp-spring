package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.infraestructura.repositorios.RepositorioResenaImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepositorioResenaTest {

    private SessionFactory sessionFactory;
    private Session sessionMock;
    private RepositorioResenaImpl repositorioResena;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioResena = new RepositorioResenaImpl(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(sessionMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaSubirUnaResenaDeUnaBicicleta() {
        // preparación
        Resena resenaMock = mock(Resena.class);
        when(resenaMock.getId()).thenReturn(1L);
        when(sessionMock.get(Resena.class, resenaMock.getId())).thenReturn(resenaMock);

        when(resenaMock.getPuntaje()).thenReturn(10);
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setPuntaje(5);
        when(resenaMock.getBicicleta()).thenReturn(bicicleta);

        // ejecución
        repositorioResena.subirResena(resenaMock);

        // validaciónAntonell@101097
        verify(sessionMock, times(1)).save(resenaMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeLasResenasDeUnaBicicleta() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(List.of());

        // ejecución
        List<Resena> resenas = repositorioResena.obtenerResenasDeUnaBicicleta(bicicletaMock);

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("SELECT r FROM Resena r WHERE r.bicicleta = :bicicleta");
        verify(queryMock).setParameter("bicicleta", bicicletaMock);
        verify(queryMock).list();
        assertEquals(0, resenas.size());
    }

}
