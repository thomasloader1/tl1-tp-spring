package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoAlquiler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public void finalizarAlquiler(Alquiler alquiler) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("UPDATE Alquiler SET estadoAlquiler = :estado WHERE id = :id");
        query.setParameter("estado", EstadoAlquiler.FINALIZADO);
        query.setParameter("id", alquiler.getId());
        query.executeUpdate();
    }
}
