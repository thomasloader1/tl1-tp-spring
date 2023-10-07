package com.tallerwebi.dominio.servicio;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Resenia;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.presentacion.DatosBicicleta;

import java.util.List;

public interface ServicioBicicleta {
    void darDeAltaUnaBicicleta(DatosBicicleta datosBicicleta) throws BicicletaValidacion;

    void darDeBajaUnaBicicleta(Long id);

    Bicicleta obtenerBicicletaPorId(Integer id);

    List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario);

    void agregarBicicleta(Bicicleta bicicleta);

    void actualizarEstadoBicicleta(Integer id, EstadoBicicleta estadoBicicleta);

    boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontrada, BicicletaNoDisponible;

    List<Resenia> verReseniasDeBicicleta(Integer id);

    boolean agregarResenia(Resenia resenia);
}
