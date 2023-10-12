package com.tallerwebi.presentacion.dto;

public class Rueda {

    float wheel_x;
    float wheel_y;
    float wheel_z;

    public Rueda(float wheel_x, float wheel_y, float wheel_z) {
        this.wheel_x = wheel_x;
        this.wheel_y = wheel_y;
        this.wheel_z = wheel_z;
    }

    public float getWheel_x() {
        return wheel_x;
    }

    public float getWheel_y() {
        return wheel_y;
    }

    public float getWheel_z() {
        return wheel_z;
    }
}
