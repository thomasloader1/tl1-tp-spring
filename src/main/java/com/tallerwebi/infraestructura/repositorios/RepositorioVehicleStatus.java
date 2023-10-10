package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.BicicletaStatus;

public interface RepositorioVehicleStatus {
    BicicletaStatus getData(String id);

}
