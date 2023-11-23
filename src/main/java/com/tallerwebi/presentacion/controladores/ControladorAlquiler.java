package com.tallerwebi.presentacion.controladores;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.servicios.ServicioAlquiler;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioMercadoPago;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosPreferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAlquiler {
    private final ServicioAlquiler servicioAlquiler;
    private final ServicioBicicleta servicioBicicleta;
    private final ServicioMercadoPago servicioMercadoPago;
    private final HttpSession session;

    @Autowired
    public ControladorAlquiler(ServicioAlquiler servicioAlquiler, ServicioBicicleta servicioBicicleta, ServicioMercadoPago servicioMercadoPago, HttpSession session) {
        this.servicioAlquiler = servicioAlquiler;
        this.servicioBicicleta = servicioBicicleta;
        this.servicioMercadoPago = servicioMercadoPago;
        this.session = session;
    }

    @RequestMapping(path = "/bicicleta/{idBicicleta}/crear-alquiler", method = RequestMethod.POST)
    public ModelAndView crearAlquiler(@PathVariable Long idBicicleta, @ModelAttribute("datosAlquiler") DatosAlquiler datosAlquiler) throws MPException, MPApiException, BicicletaNoEncontrada, AlquilerValidacion {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelMap modelo = new ModelMap();
        Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(idBicicleta);
        datosAlquiler.setBicicleta(bicicleta);
        datosAlquiler.setUsuario(usuario);
        if (datosAlquiler.getCantidadHoras() == null || datosAlquiler.getCantidadHoras() < 1) {
            modelo.put("error", "La cantidad de horas debe ser mayor a 0");
            return new ModelAndView("redirect:/bicicleta/" + bicicleta.getId() + "/detalle", modelo);
        }
        Alquiler alquiler = servicioAlquiler.comenzarAlquiler(datosAlquiler);
        session.setAttribute("alquilerAux", alquiler);
        DatosPreferencia preference = servicioMercadoPago.crearPreferenciaPago(alquiler);
        modelo.put("idPreferencia", preference);
        return new ModelAndView("redirect:" + preference.urlCheckout, modelo);
    }

    @RequestMapping(path = "/validar-pago", method = RequestMethod.GET)
    public ModelAndView validarPago(@RequestParam("status") String status) throws AlquilerValidacion {
        ModelMap modelo = new ModelMap();
        Alquiler alquiler = (Alquiler) session.getAttribute("alquilerAux");
        if (status.equals("approved")) {
            session.setAttribute("alquiler", alquiler);
            session.removeAttribute("alquilerAux");
            servicioAlquiler.crearAlquiler(alquiler);
        } else {
            session.removeAttribute("alquilerAux");
            modelo.put("error", "Error al procesar el pago.");
            modelo.put("bicicleta", alquiler.getBicicleta());
            String viewName = "redirect:/bicicleta/" + alquiler.getBicicleta().getId() + "/detalle";
            return new ModelAndView(viewName, modelo);
        }
        return new ModelAndView("redirect:/mapa");
    }

    @RequestMapping(path = "/mis-alquileres", method = RequestMethod.GET)
    public ModelAndView misAlquileres() {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            Alquiler alquiler = (Alquiler) session.getAttribute("alquiler");
            if (alquiler != null) {
                return new ModelAndView("redirect:/mapa");
            }
            if (session.getAttribute("resena") != null) {
                return new ModelAndView("redirect:/bicicleta/" + session.getAttribute("resena") + "/crear-resena");
            }
            ModelMap modelo = new ModelMap();
            List<Alquiler> alquileres = servicioAlquiler.obtenerAlquileresDelUsuario(usuario);
            modelo.put("usuario", usuario);
            modelo.put("alquileres", alquileres);
            return new ModelAndView("mis-alquileres", modelo);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/alquiler/{idAlquiler}/finalizar-alquiler", method = RequestMethod.GET)
    public ModelAndView finalizarAlquiler(@PathVariable("idAlquiler") Long id) {
        if (session.getAttribute("resena") != null) {
            return new ModelAndView("redirect:/bicicleta/" + session.getAttribute("resena") + "/crear-resena");
        }
        Alquiler alquiler = (Alquiler) session.getAttribute("alquiler");
        if (alquiler != null) {
            Bicicleta bicicleta = servicioAlquiler.obtenerBicicletaPorIdDeAlquiler(id);
            servicioAlquiler.finalizarAlquiler(id);
            String viewName = "redirect:/bicicleta/" + bicicleta.getId() + "/crear-resena";
            session.removeAttribute("alquiler");
            session.setAttribute("resena", bicicleta.getId());
            return new ModelAndView(viewName);
        }
        return new ModelAndView("redirect:/home");
    }
}
