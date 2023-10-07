package com.tallerwebi.dominio.bicicleta;

import com.tallerwebi.dominio.resenia.Resenia;

import java.util.List;

public interface ServicioBicicleta {

    Bicicleta obtenerBicicletaPorId(Integer id);

    void agregarBicicleta(Bicicleta bicicleta);

    void actualizarEstadoBicicleta(Integer id, Estado estado);

    boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontradaException, BicicletaNoDisponibleException;
    List<Resenia> verReseniasDeBicicleta(Integer id);

    boolean agregarResenia(Resenia resenia);

    List<Bicicleta> getBicicletas();

    Bicicleta getBicicleta();
}
