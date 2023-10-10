package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Usuario WHERE email = :email AND password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        return (Usuario) query.uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Usuario WHERE email = :email");
        query.setParameter("email", email);
        return (Usuario) query.uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }
}