package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioAlquilerBicicletas")
public class RepositorioAlquilerImpl implements RepositorioAlquiler {


    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioAlquilerImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Alquiler obtenerAlquilerporId(Long id) {
        return sessionFactory.getCurrentSession().get(Alquiler.class, id);
    }

    @Override
    public void crearAlquiler(Alquiler alquiler) {
        sessionFactory.getCurrentSession().save(alquiler);
    }

    @Override
    public Alquiler finalizarAlquiler(Alquiler alquiler) {
        alquiler.setEstadoAlquiler(EstadoAlquiler.FINALIZADO);
        sessionFactory.getCurrentSession().update(alquiler);
        return alquiler;
    }

    @Override
    public Alquiler modificarAlquiler(Alquiler alquiler) {
        return null;
    }


    @Override
    public List<Alquiler> obtenerAlquilerDeBicicletas(Bicicleta bicicleta) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT r FROM Alquiler r WHERE r.bicicleta = :bicicleta");
        query.setParameter("bicicleta", bicicleta);
        return (List<Alquiler>) query.list();
    }

    @Override
    public List<Alquiler> obtenerBicicletasAlquiladas() {
        return null;
    }
}
