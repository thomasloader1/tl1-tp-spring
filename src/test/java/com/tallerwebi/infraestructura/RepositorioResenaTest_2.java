package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateTestConfig;
import com.tallerwebi.config.SpringWebTestConfig;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicletaImpl;
import com.tallerwebi.infraestructura.repositorios.RepositorioResena;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioResenaTest_2 {

    @Autowired
    private RepositorioResena repositorioResena;
    @Autowired
    private  SessionFactory sessionFactory;

    private RepositorioBicicleta repositorioBicicleta;

    @BeforeEach
    public void init(){
        repositorioBicicleta = new RepositorioBicicletaImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerLaCantidadDeResenasParaUnaBicicleta() {
        Bicicleta bicicleta = new Bicicleta();
        Resena resena = new Resena();
        resena.setBicicleta(bicicleta);

        sessionFactory.getCurrentSession().save(bicicleta);
        sessionFactory.getCurrentSession().save(resena);

        int cantResenas = repositorioResena.obtenerCantidadDeResenasParaUnaBicicleta(bicicleta);


        assertEquals(1,cantResenas);
    }

    @Test
    @Transactional
    @Rollback
    public void queAlGuardarUnaResenaTambienSeActualiceElPuntajeDeLaBici() {
        Resena resena = new Resena();
        Bicicleta bicicleta = new Bicicleta();

        bicicleta.setPuntaje(10);

        resena.setBicicleta(bicicleta);
        resena.setPuntaje(5);

        sessionFactory.getCurrentSession().save(bicicleta);

        //Simula lo que hace el sercico porque ahi esta la suma
        resena.getBicicleta().setPuntaje(resena.getBicicleta().getPuntaje() + resena.getPuntaje());

        repositorioResena.subirResena(resena);

        Bicicleta biciObtenida = repositorioBicicleta.obtenerBicicletaPorId(1L);

        assertNotNull(biciObtenida);
        assertEquals(bicicleta, biciObtenida);
        assertEquals(biciObtenida.getPuntaje(), 15);
    }
}
