package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.infraestructura.repositorios.RepositorioMapaImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RepositorioMapaTest {
    private SessionFactory sessionFactory;
    private Session sessionMock;
    private RepositorioMapaImpl repositorioMapa;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioMapa = new RepositorioMapaImpl(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(sessionMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeTodosLosPropietarios() {
        // preparación
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(List.of());

        // ejecución
        List<Usuario> propietarios = repositorioMapa.obtenerPropietarios();

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("SELECT u FROM Usuario u WHERE u.rol = 'Propietario'");
        verify(queryMock).list();
        assertNotNull(propietarios);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeCincoPropietarios() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(List.of(usuarioMock, usuarioMock, usuarioMock, usuarioMock, usuarioMock));

        // ejecución
        List<Usuario> propietarios = repositorioMapa.obtenerPropietarios();

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("SELECT u FROM Usuario u WHERE u.rol = 'Propietario'");
        verify(queryMock).list();
        assertEquals(5, propietarios.size());
    }

}
