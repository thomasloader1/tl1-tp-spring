package com.tallerwebi.dominio.bicicleta;

import com.tallerwebi.presentacion.DatosLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BicicletaController {

    private ServicioBicicleta servicioBicicleta;
    @Autowired
    public BicicletaController(ServicioBicicleta servicioBicicleta) {
        this.servicioBicicleta = servicioBicicleta;
    }
    @RequestMapping("/bicicletas")

    public ModelAndView index() {
        ModelMap data = new ModelMap();
        data.put("message","Bienvenido");
        data.put("bicicletas", servicioBicicleta.getBicicletas());
        return new ModelAndView("bicicletas", data);
    }
}
