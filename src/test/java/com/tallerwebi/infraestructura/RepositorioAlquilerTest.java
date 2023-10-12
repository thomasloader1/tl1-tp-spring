package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateTestConfig;
import com.tallerwebi.config.SpringWebConfig;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import com.tallerwebi.infraestructura.repositorios.RepositorioAlquilerImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, HibernateTestConfig.class})

public class RepositorioAlquilerTest {
    @Autowired
    private SessionFactory sessionFactory;
    private Session sessionMock;
    private RepositorioAlquilerImpl repositorioAlquiler;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
       repositorioAlquiler = new RepositorioAlquilerImpl(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(sessionMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaCrearElAlquilerDeUnaBicicleta(){
        // preparación
        Alquiler alquilerMock = mock(Alquiler.class);
        when(alquilerMock.getId()).thenReturn(1L);
        when(sessionMock.get(Alquiler.class, alquilerMock.getId())).thenReturn(alquilerMock);

        // ejecución
        repositorioAlquiler.crearAlquiler(alquilerMock);

        // validación
        verify(sessionMock, times(1)).save(alquilerMock);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnaListaDeLosAlquileresDeUnaBicicleta() {
        // preparación
        Bicicleta bicicletaMock = mock(Bicicleta.class);
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(List.of());

        // ejecución
        List<Alquiler> alquileres = repositorioAlquiler.obtenerAlquilerDeBicicletas(bicicletaMock);

        // validación
        verify(sessionMock, times(1)).createQuery(anyString());
        verify(sessionMock).createQuery("SELECT r FROM Alquiler r WHERE r.bicicleta = :bicicleta");
        verify(queryMock).setParameter("bicicleta", bicicletaMock);
        verify(queryMock).list();
        assertEquals(0, alquileres.size());

    }




}