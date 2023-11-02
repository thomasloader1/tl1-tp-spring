package com.tallerwebi.infraestructura.repositorios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import com.tallerwebi.dominio.entidad.Usuario;

import java.util.List;

public interface RepositorioBicicleta {
    Bicicleta obtenerBicicletaPorId(Long id);

    void registrarBicicleta(Bicicleta bicicleta);

    void eliminarBicicleta(Bicicleta bicicleta);

    List<Bicicleta> obtenerBicicletasDelUsuario(Usuario usuario);

    List<Bicicleta> obtenerBicicletasDisponiblesPorIdUsuario(Long id);

    void updateEstado(Long id, EstadoBicicleta nuevoEstado);

    List<Bicicleta> obtenerBicicletas();

    List<Bicicleta> obtenerBicicletasDisponibles();

    List<Bicicleta> obtenerBiciclestasEnReparacion();
}
