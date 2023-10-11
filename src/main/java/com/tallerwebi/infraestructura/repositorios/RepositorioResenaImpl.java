package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioResena")
public class RepositorioResenaImpl implements RepositorioResena {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioResenaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void subirResena(Resena resena) {
        sessionFactory.getCurrentSession().save(resena);
    }

    @Override
    public List<Resena> obtenerResenasDeUnaBicicleta(Bicicleta bicicleta) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT r FROM Resena r WHERE r.bicicleta = :bicicleta");
        query.setParameter("bicicleta", bicicleta);
        return (List<Resena>) query.list();
    }
}
