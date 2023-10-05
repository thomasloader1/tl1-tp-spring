package com.tallerwebi.dominio.servicio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Resenia;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;

import java.util.List;

public interface ServicioBicicleta {

    Bicicleta obtenerBicicletaPorId(Integer id);

    void agregarBicicleta(Bicicleta bicicleta);

    void actualizarEstadoBicicleta(Integer id, EstadoBicicleta estadoBicicleta);

    boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontrada, BicicletaNoDisponible;

    List<Resenia> verReseniasDeBicicleta(Integer id);

    boolean agregarResenia(Resenia resenia);
}
