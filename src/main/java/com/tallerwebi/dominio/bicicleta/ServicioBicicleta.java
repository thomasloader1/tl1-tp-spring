package com.tallerwebi.dominio.bicicleta;

public interface ServicioBicicleta {

    Bicicleta obtenerBicicletaPorId(Integer id);

    void agregarBicicleta(Bicicleta bicicleta);

    void actualizarEstadoBicicleta(Integer id, Estado estado);

    boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontradaException, BicicletaNoDisponibleException;
}
