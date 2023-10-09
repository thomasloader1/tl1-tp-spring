package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepositorioUsuarioTest {
    private Criteria criteriaMock;
    private SessionFactory sessionFactory;
    private Session sessionMock;
    private RepositorioUsuarioImpl repositorioUsuario;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        criteriaMock = mock(Criteria.class);
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(sessionMock);
        usuarioMock = mock(Usuario.class);
    }

    @Test
    public void queSePuedaGuardarUnUsuario() {
        // preparación
        when(usuarioMock.getId()).thenReturn(1L);
        when(sessionMock.get(Usuario.class, usuarioMock.getId())).thenReturn(usuarioMock);

        // ejecución
        repositorioUsuario.guardar(usuarioMock);

        // validación
        verify(sessionMock, times(1)).save(usuarioMock);
    }

    @Test
    public void queSePuedaModificarUnUsuario() {
        // preparación
        when(usuarioMock.getId()).thenReturn(1L);
        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        when(sessionMock.get(Usuario.class, usuarioMock.getId())).thenReturn(usuarioMock);

        // ejecución
        repositorioUsuario.modificar(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("nuevoemail@mail.com");

        // validación
        verify(sessionMock, times(1)).update(usuarioMock);
        assertEquals("nuevoemail@mail.com", usuarioMock.getEmail());
    }

    @Test
    public void queSePuedaBuscarUnUsuarioConEmailYContrasena() {
        // preparación
        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        when(usuarioMock.getPassword()).thenReturn("1234");
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(usuarioMock);

        // ejecución
        Usuario usuario = repositorioUsuario.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword());

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("FROM Usuario WHERE email = :email AND password = :password");
        verify(queryMock).setParameter("email", usuarioMock.getEmail());
        verify(queryMock).setParameter("password", usuarioMock.getPassword());
        assertEquals(usuarioMock, usuario);
    }

    @Test
    public void queSePuedaBuscarUnUsuarioPorEmail() {
        // preparación
        when(usuarioMock.getEmail()).thenReturn("usuario@mail.com");
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(usuarioMock);

        // ejecución
        Usuario usuario = repositorioUsuario.buscarUsuarioPorEmail(usuarioMock.getEmail());

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("FROM Usuario WHERE email = :email");
        verify(queryMock).setParameter("email", usuarioMock.getEmail());
        assertEquals(usuarioMock, usuario);
    }
}