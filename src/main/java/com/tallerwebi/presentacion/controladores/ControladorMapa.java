package com.tallerwebi.presentacion.controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.DistanciaComparador;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ControladorMapa {
    private final ServicioMapa servicioMapa;
    private final HttpSession session;

    @Autowired
    public ControladorMapa(ServicioMapa servicioMapa, HttpSession session) {
        this.servicioMapa = servicioMapa;
        this.session = session;
    }

    @RequestMapping(path = "/mapa", method = RequestMethod.GET)
    public ModelAndView irAMapa() throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            Alquiler alquiler = (Alquiler) session.getAttribute("alquiler");

            if (session.getAttribute("resena") != null) {
                return new ModelAndView("redirect:/bicicleta/" + session.getAttribute("resena") + "/crear-resena");
            }

            ModelMap modelo = new ModelMap();

            Coordenada coordenada = servicioMapa.obtenerUbicacionActual();
            modelo.put("latitudActual", coordenada.getLatitude());
            modelo.put("longitudActual", coordenada.getLongitude());

            if (alquiler != null) {
                if (Duration.between(alquiler.getFechaAlquiler(), LocalDateTime.now()).getSeconds() > (alquiler.getCantidadHoras() * 3600)) {
                    return new ModelAndView("redirect:/alquiler/" + alquiler.getId() + "/finalizar-alquiler");
                }

                modelo.put("alquiler", alquiler);
                modelo.put("tiempoRestante", Duration.between(alquiler.getFechaAlquiler(), LocalDateTime.now()));
                
                return new ModelAndView("mapa-alquiler", modelo);
            }

            List<Usuario> propietarios = servicioMapa.obtenerPropietarios();
            DistanciaComparador comparador = new DistanciaComparador(coordenada.getLatitude(), coordenada.getLongitude());
            propietarios.sort(comparador);

            modelo.put("usuario", usuario);
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
