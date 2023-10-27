package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Usuario;

import java.util.Comparator;

public class DistanciaComparador implements Comparator<Usuario> {
    private Double latitudActual;
    private Double longitudActual;

    public DistanciaComparador(Double latitudActual, Double longitudActual) {
        this.latitudActual = latitudActual;
        this.longitudActual = longitudActual;
    }

    @Override
    public int compare(Usuario o1, Usuario o2) {
        Double distancia1 = Math.sqrt(Math.pow(o1.getLatitud() - latitudActual, 2) + Math.pow(o1.getLongitud() - longitudActual, 2));
        Double distancia2 = Math.sqrt(Math.pow(o2.getLatitud() - latitudActual, 2) + Math.pow(o2.getLongitud() - longitudActual, 2));

        return distancia1.compareTo(distancia2);
    }
}