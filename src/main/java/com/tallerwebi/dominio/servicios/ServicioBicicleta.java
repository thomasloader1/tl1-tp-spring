package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Resenia;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoDisponible;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.presentacion.dto.DatosBicicleta;

import java.util.List;

public interface ServicioBicicleta {
    void darDeAltaUnaBicicleta(DatosBicicleta datosBicicleta) throws BicicletaValidacion;

    void darDeBajaUnaBicicleta(Long id);

    Bicicleta obtenerBicicletaPorId(Long id);
    List<Bicicleta> obtenerTodasLasBicicleta();

    List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario);

    void actualizarEstadoBicicleta(Long id, EstadoBicicleta estadoBicicleta);

    boolean verificarDisponibilidad(Integer id) throws BicicletaNoEncontrada, BicicletaNoDisponible;

    List<Resenia> verReseniasDeBicicleta(Integer id);

    boolean agregarResenia(Resenia resenia);
}
