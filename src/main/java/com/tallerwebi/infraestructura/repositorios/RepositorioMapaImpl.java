package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioMapa")
public class RepositorioMapaImpl implements RepositorioMapa {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioMapaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Usuario> obtenerPropietarios() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT u FROM Usuario u WHERE u.rol = 'Propietario'");
        return (List<Usuario>) query.list();
    }
}
