package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.servicios.ServicioAlquiler;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAlquiler {
    private final ServicioAlquiler servicioAlquiler;
    private final ServicioBicicleta servicioBicicleta;

    @Autowired
    public ControladorAlquiler(ServicioAlquiler servicioAlquiler, ServicioBicicleta servicioBicicleta) {
        this.servicioAlquiler = servicioAlquiler;
        this.servicioBicicleta = servicioBicicleta;
    }

    @ModelAttribute("usuario")
    public Usuario obtenerUsuarioDeSesion(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    @RequestMapping(path = "/bicicleta/{idBicicleta}/crear-alquiler", method = RequestMethod.POST)
    public ModelAndView crearAlquiler(@PathVariable Long idBicicleta, @ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosAlquiler") DatosAlquiler datosAlquiler) {
        ModelMap modelo = new ModelMap();
        try {
            Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(idBicicleta);
            datosAlquiler.setBicicleta(bicicleta);
            datosAlquiler.setUsuario(usuario);
            servicioAlquiler.crearAlquiler(datosAlquiler);
        } catch (AlquilerValidacion e) {
            modelo.put("error", "Error al crear el Alquiler");
            return new ModelAndView("crear-alquiler", modelo);
        } catch (BicicletaNoEncontrada e) {
            modelo.put("error", "Error al crear el Alquiler");
            return new ModelAndView("crear-alquiler", modelo);
        }
        return new ModelAndView("redirect:/mapa");
    }

    @RequestMapping(path = "/mis-alquileres", method = RequestMethod.GET)
    public ModelAndView verAlquiler(@ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosAlquiler") DatosAlquiler datosAlquiler) {
        ModelMap modelo = new ModelMap();
        datosAlquiler.setUsuario(usuario);
        List<Alquiler> alquileres = servicioAlquiler.obtenerAlquileresDeUnaBicicleta(datosAlquiler);

        modelo.put("usuario", usuario);
        modelo.put("alquileres", alquileres);
        return new ModelAndView("mis-alquileres", modelo);

    }

    @RequestMapping(path = "/alquiler/{idAlquiler}/finalizar-alquiler", method = RequestMethod.GET)
    public ModelAndView finalizarAlquiler(@PathVariable("idAlquiler") Long id) {

        Bicicleta bicicleta = servicioAlquiler.obtenerBicicletaPorIdDeAlquiler(id);
        servicioAlquiler.finalizarAlquiler(id);
        String viewName = "redirect:/bicicleta/" + bicicleta.getId() + "/crear-resena";
        ModelAndView mav = new ModelAndView(viewName);
        mav.getModel().put("message", "Se fina");

        return mav;
    }
}
