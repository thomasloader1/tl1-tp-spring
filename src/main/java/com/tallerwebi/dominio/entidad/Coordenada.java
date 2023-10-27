package com.tallerwebi.dominio.entidad;

public class Coordenada {
    private double latitude;
    private double longitude;

    public Coordenada() {
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

