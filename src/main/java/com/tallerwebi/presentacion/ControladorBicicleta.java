package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicio.ServicioBicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorBicicleta {


    private ServicioBicicleta servicioBicicleta;

    @Autowired
    public ControladorBicicleta(ServicioBicicleta servicioBicicleta) {
        this.servicioBicicleta = servicioBicicleta;
    }

}
