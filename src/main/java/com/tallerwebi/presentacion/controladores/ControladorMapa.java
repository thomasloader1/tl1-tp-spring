package com.tallerwebi.presentacion.controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.DistanciaComparador;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ModelAndView irAMapa(@ModelAttribute("usuario") Usuario usuario) throws JsonProcessingException {
        if (usuario != null) {
            ModelMap modelo = new ModelMap();

            Coordenada coordenada = servicioMapa.obtenerUbicacionActual();
            modelo.put("latitudActual", coordenada.getLatitude());
            modelo.put("longitudActual", coordenada.getLongitude());

            List<Usuario> propietarios = servicioMapa.obtenerPropietarios();
            DistanciaComparador comparador = new DistanciaComparador(coordenada.getLatitude(), coordenada.getLongitude());
            propietarios.sort(comparador);

            modelo.put("propietarios", propietarios);
            modelo.put("distancias", servicioMapa.obtenerDistancias(propietarios));
            modelo.put("tiemposDeLlegadaCaminando", servicioMapa.obtenerTiempoDeLlegadaCaminando(propietarios));
            modelo.put("tiemposDeLlegadaEnBicicleta", servicioMapa.obtenerTiempoDeLlegadaEnBicicleta(propietarios));
            modelo.put("tiemposDeLlegadaEnAuto", servicioMapa.obtenerTiempoDeLlegadaEnAuto(propietarios));

            return new ModelAndView("mapa", modelo);
        }
        return new ModelAndView("redirect:/login");
    }
    
}
