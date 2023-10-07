package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.repositorio.RepositorioBicicleta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioBicicleta")
public class RepositorioBicicletaImpl implements RepositorioBicicleta {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioBicicletaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Bicicleta obtenerBicicletaPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Bicicleta.class, id);
    }

    @Override
    public void registrarBicicleta(Bicicleta bicicleta) {
        sessionFactory.getCurrentSession().save(bicicleta);
    }

    @Override
    public void eliminarBicicleta(Bicicleta bicicleta) {
        sessionFactory.getCurrentSession().delete(bicicleta);
    }

    @Override
    public List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b WHERE b.usuario = :usuario");
        query.setParameter("usuario", usuario);
        return (List<Bicicleta>) query.list();
    }

    @Override
    public List<Bicicleta> obtenerBicicletas() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b");
        return (List<Bicicleta>) query.list();
    }
}
