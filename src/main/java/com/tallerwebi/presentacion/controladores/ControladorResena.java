package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioResena;
import com.tallerwebi.presentacion.dto.DatosResena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorResena {
    private final ServicioResena servicioResena;
    private final ServicioBicicleta servicioBicicleta;

    @Autowired
    public ControladorResena(ServicioResena servicioResena, ServicioBicicleta servicioBicicleta) {
        this.servicioResena = servicioResena;
        this.servicioBicicleta = servicioBicicleta;
    }

    @ModelAttribute("usuario")
    public Usuario obtenerUsuarioDeSesion(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    @ModelAttribute("alquiler")
    public Alquiler obtenerAlquilerDeSesion(HttpSession session) {
        return (Alquiler) session.getAttribute("alquiler");
    }

    @RequestMapping(path = "/bicicleta/{idBicicleta}/crear-resena", method = RequestMethod.GET)
    public ModelAndView irACrearResena(@PathVariable Long idBicicleta, @ModelAttribute("usuario") Usuario usuario, @ModelAttribute("alquiler") Alquiler alquiler) {
        if (usuario != null) {
            if (alquiler != null) {
                return new ModelAndView("redirect:/mapa");
            }
            ModelMap modelo = new ModelMap();
            try {
                servicioBicicleta.obtenerBicicletaPorId(idBicicleta);
            } catch (BicicletaNoEncontrada e) {
                return new ModelAndView("pagina-no-encontrada");
            }
            modelo.put("datosResena", new DatosResena());
            modelo.put("rol", usuario.getRol());
            modelo.put("idBicicleta", idBicicleta);
            return new ModelAndView("crear-resena", modelo);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/bicicleta/{idBicicleta}/subir-resena", method = RequestMethod.POST)
    public ModelAndView subirResena(@PathVariable Long idBicicleta, @ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosResena") DatosResena datosResena) {
        ModelMap modelo = new ModelMap();
        try {
            Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(idBicicleta);
            datosResena.setBicicleta(bicicleta);
            datosResena.setUsuario(usuario);
            servicioResena.subirResena(datosResena);
        } catch (ResenaValidacion e) {
            modelo.put("error", "Debe completar todos los campos");
            return new ModelAndView("crear-resena", modelo);
        } catch (ResenaPuntajeValidacion e) {
            modelo.put("error", "El puntaje debe ser entre 1 y 5 estrellas");
            return new ModelAndView("crear-resena", modelo);
        } catch (BicicletaNoEncontrada e) {
            modelo.put("error", "Error al crear la reseña");
            return new ModelAndView("crear-resena", modelo);
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/bicicleta/{idBicicleta}/resenas", method = RequestMethod.GET)
    public ModelAndView irAResenasDeUnaBicicleta(@PathVariable Long idBicicleta, @ModelAttribute("usuario") Usuario usuario, @ModelAttribute("alquiler") Alquiler alquiler) {
        if (usuario != null) {
            if (alquiler != null) {
                return new ModelAndView("redirect:/mapa");
            }
            ModelMap modelo = new ModelMap();
            try {
                Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(idBicicleta);
                modelo.put("resenas", servicioResena.obtenerResenasDeUnaBicicleta(bicicleta));
            } catch (BicicletaNoEncontrada e) {
                return new ModelAndView("pagina-no-encontrada");
            }
            return new ModelAndView("resenas", modelo);
        }
        return new ModelAndView("redirect:/login");
    }
}