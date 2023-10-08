package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.BicicletaStatus;

public interface RepositorioVehicleStatus {
    BicicletaStatus getData(String id);

}
