package com.tallerwebi.dominio.bicicleta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BicicletaController {


    private ServicioBicicleta servicioBicicleta;
    @Autowired
    public BicicletaController(ServicioBicicleta servicioBicicleta) {
        this.servicioBicicleta = servicioBicicleta;
    }

}
