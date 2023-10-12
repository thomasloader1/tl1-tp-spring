package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Condition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioRiskScoreImp implements RepositorioRiskScore {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Condition getCondicionByID(long bici_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b.condicion FROM Bicicleta b WHERE b.id = :id");
        query.setParameter("id", bici_id);
        return (Condition) query.uniqueResult();
    }

    @Override
    public void guardarBici(Bicicleta bicicleta) {
        sessionFactory.getCurrentSession().save(bicicleta);
    }
}
