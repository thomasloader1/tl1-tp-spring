package com.tallerwebi.presentacion.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.AlquilerValidacion;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.servicios.ServicioAlquiler;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioMercadoPago;
import com.tallerwebi.presentacion.dto.*;
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

    @Autowired
    public ControladorAlquiler(ServicioAlquiler servicioAlquiler, ServicioBicicleta servicioBicicleta, ServicioMercadoPago servicioMercadoPago) {
        this.servicioAlquiler = servicioAlquiler;
        this.servicioBicicleta = servicioBicicleta;
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @ModelAttribute("usuario")
    public Usuario obtenerUsuarioDeSesion(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    @RequestMapping(path = "/bicicleta/{idBicicleta}/crear-alquiler", method = RequestMethod.POST)
    public ModelAndView crearAlquiler(@PathVariable Long idBicicleta, @ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosAlquiler") DatosAlquiler datosAlquiler) throws MPException, MPApiException {
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
      Preference preference = servicioMercadoPago.crearPreferenciaPago(datosAlquiler);
      modelo.put("idPreferencia",preference);
      return new ModelAndView("detalle-pago", modelo);
    }

    @RequestMapping(path = "/mis-alquileres", method = RequestMethod.GET)
    public ModelAndView verAlquiler(@ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosAlquiler") DatosAlquiler datosAlquiler) {
        if (usuario != null) {
            ModelMap modelo = new ModelMap();
            datosAlquiler.setUsuario(usuario);
            List<Alquiler> alquileres = servicioAlquiler.obtenerAlquileresDelUsuario(datosAlquiler);
            modelo.put("usuario", usuario);
            modelo.put("alquileres", alquileres);
            return new ModelAndView("mis-alquileres", modelo);
        }
        return new ModelAndView("redirect:/login");
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

    @RequestMapping(path = "/bicicleta/{idBicicleta}/crear-pago", method = RequestMethod.POST)
    public ModelAndView crearPago(@PathVariable Long idBicicleta, @ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosAlquiler") DatosAlquiler datosAlquiler) {
        ModelMap modelo = new ModelMap();
        try {
            Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(idBicicleta);
            datosAlquiler.setBicicleta(bicicleta);
            datosAlquiler.setUsuario(usuario);
            servicioAlquiler.crearAlquiler(datosAlquiler);

            // Crear preferencia de pago con Mercado Pago
            Preference idPreferencia = servicioMercadoPago.crearPreferenciaPago(datosAlquiler);

            // Pasa el idPreferencia a la vista
            modelo.put("idPreferencia", idPreferencia);

            // Redirigir al usuario a la página de pago de Mercado Pago con el idPreferencia generado
            return new ModelAndView("redirect:/mapa", modelo);
        } catch (BicicletaNoEncontrada e) {
            modelo.put("error", "Error al crear el Alquiler");
            return new ModelAndView("redirect:/mapa", modelo);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        } catch (AlquilerValidacion e) {
            throw new RuntimeException(e);
        }
    }
}
