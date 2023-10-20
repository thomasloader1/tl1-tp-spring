package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateTestConfig;
import com.tallerwebi.config.SpringWebConfig;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Condition;
import com.tallerwebi.infraestructura.repositorios.RepositorioRiskScore;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, HibernateTestConfig.class})
public class RepositorioRiskScoreTest {
    @Autowired
    private  SessionFactory sessionFactory;
    private Session sessionMock;

    @Autowired
    private RepositorioRiskScore repositorioRiskScore;

//    @BeforeEach
//    public void init(){
//        sessionFactory = mock(SessionFactory.class);
//        sessionMock = mock(Session.class);
//        when(sessionFactory.getCurrentSession()).thenReturn(sessionMock);
//    }

    @Test @Transactional @Rollback
    public void sePuedeObtenerLaCondicion(){
        final Condition EX_COND = Condition.BUENO_ESTADO;

        Bicicleta bicicleta = makeBici();
        bicicleta.setCondicion(Condition.BUENO_ESTADO);

       sessionFactory.getCurrentSession().save(bicicleta);

        Condition condicion = repositorioRiskScore.getCondicionByID(1L);

        assertEquals(EX_COND, condicion);
    }

    private Bicicleta makeBici(){
        Bicicleta bici = new Bicicleta();

        bici.setId(1L);
        bici.setCondicion(Condition.PERFECTO_ESTADO);

        return bici;
    }
}
