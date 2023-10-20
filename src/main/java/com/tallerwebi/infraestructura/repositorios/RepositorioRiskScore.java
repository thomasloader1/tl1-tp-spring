package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Condition;

import javax.transaction.Transactional;

@Transactional
public interface RepositorioRiskScore {

    Condition getCondicionByID(long bici_id);

    void guardarBici(Bicicleta bicicleta);

}
