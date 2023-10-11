package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.BicicletaStatus;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("vehicleStatusRepositorio")
public class RepositorioVehicleStatusImpl implements RepositorioVehicleStatus{

    private final SessionFactory sessionFactory;

    public RepositorioVehicleStatusImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BicicletaStatus getData(String id) {
        return sessionFactory.getCurrentSession().get(BicicletaStatus.class, id);
    }
}
