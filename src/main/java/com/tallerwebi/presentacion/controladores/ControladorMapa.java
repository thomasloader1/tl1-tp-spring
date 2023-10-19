package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorMapa {
    private final ServicioMapa servicioMapa;

    @Autowired
    public ControladorMapa(ServicioMapa servicioMapa) {
        this.servicioMapa = servicioMapa;
    }


    @ModelAttribute("usuario")
    public Usuario obtenerUsuarioDeSesion(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    @RequestMapping(path = "/mapa", method = RequestMethod.GET)
    public ModelAndView irAMapa() {
        ModelMap modelo = new ModelMap();
        modelo.put("propietarios", servicioMapa.obtenerPropietarios());
        return new ModelAndView("mapa", modelo);
    }
}
