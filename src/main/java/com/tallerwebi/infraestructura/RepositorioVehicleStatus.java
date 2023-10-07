package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.StatusDAO;

public interface RepositorioVehicleStatus {
    StatusDAO getData(String id);

}
