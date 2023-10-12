package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioAlquilerBicicletas")
public class RepositorioAlquilerImpl implements RepositorioAlquiler {


    private SessionFactory sessionFactory;

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
    public void finalizarAlquiler(Alquiler alquiler) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("UPDATE Alquiler SET estadoAlquiler = :estado WHERE id = :id");
        query.setParameter("estado", EstadoAlquiler.FINALIZADO);
        query.setParameter("id", alquiler.getId());
        query.executeUpdate();
    }

    @Override
    public List<Alquiler> obtenerAlquilerDeBicicletas(Bicicleta bicicleta) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT r FROM Alquiler r WHERE r.bicicleta = :bicicleta");
        query.setParameter("bicicleta", bicicleta);
        return (List<Alquiler>) query.list();
    }
}
