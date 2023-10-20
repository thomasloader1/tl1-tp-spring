package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.BicicletaStatus;
import com.tallerwebi.dominio.entidad.EstadoBicicleta;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMantenimiento {

    @RequestMapping(path = "propietario/mantenimiento/")
    public ModelAndView irAMantenimiento(){

        return new ModelAndView("mantenimiento");
    }

    @RequestMapping(path = "propietario/mantenimiento/update")
    public ModelAndView updateStatus(@ModelAttribute("status")BicicletaStatus status){
        
        return new ModelAndView("mantenimiento");
    }
}
