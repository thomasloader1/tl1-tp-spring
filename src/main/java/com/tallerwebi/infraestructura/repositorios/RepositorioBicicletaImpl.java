package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
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
    public List<Bicicleta> obtenerBicicletasDisponiblesPorIdUsuario(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b WHERE b.usuario.id = :id AND b.estadoBicicleta = :estado");
        query.setParameter("id", id);
        query.setParameter("estado", EstadoBicicleta.DISPONIBLE);
        return (List<Bicicleta>) query.list();
    }
    public List<Bicicleta> obtenerBicicletasEnReparacionPorIdUsuario(Long id){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b WHERE b.usuario.id = :id AND b.estadoBicicleta = :estado");
        query.setParameter("id", id);
        query.setParameter("estado", EstadoBicicleta.REQUIERE_REPARACION);
        return (List<Bicicleta>) query.list();
    }

    @Override
    public List<Bicicleta> obtenerBicicletasEnUsoPorIdUsuario(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b WHERE b.usuario.id = :id AND b.estadoBicicleta = :estado");
        query.setParameter("id", id);
        query.setParameter("estado", EstadoBicicleta.EN_USO);
        return (List<Bicicleta>) query.list();
    }

    @Override
    public void updateEstado(Long id, EstadoBicicleta estadoBicicleta) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b WHERE b.id = :id");

        query.setParameter("id", id);
        Bicicleta bicicleta = (Bicicleta) query.uniqueResult();
        bicicleta.setEstadoBicicleta(estadoBicicleta);
        session.update(bicicleta);
    }
    public List<Bicicleta> obtenerBicicletas() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b");
        return (List<Bicicleta>) query.list();

    }

    public List<Bicicleta> obtenerBicicletasDisponibles() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Bicicleta b WHERE b.estadoBicicleta = :estado");
        query.setParameter("estado",EstadoBicicleta.DISPONIBLE);

        return (List<Bicicleta>) query.list();
    }



}
