package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioResena;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosBicicleta;
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
public class ControladorBicicleta {
    private final ServicioBicicleta servicioBicicleta;
    private final ServicioResena servicioResena;
    private final HttpSession session;

    @Autowired
    public ControladorBicicleta(ServicioBicicleta servicioBicicleta, ServicioResena servicioResena, HttpSession session) {
        this.servicioBicicleta = servicioBicicleta;
        this.servicioResena = servicioResena;
        this.session = session;
    }

    @RequestMapping(path = "/registrar-bicicleta", method = RequestMethod.GET)
    public ModelAndView irARegistrarBicicleta() {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            if (verificarSiEsPropietario(usuario)) {
                ModelMap modelo = new ModelMap();
                modelo.put("datosBicicleta", new DatosBicicleta());
                modelo.put("usuario", usuario);
                return new ModelAndView("registrar-bicicleta", modelo);
            }
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/alta-bicicleta", method = RequestMethod.POST)
    public ModelAndView darDeAltaUnaBicicleta(@ModelAttribute("datosBicicleta") DatosBicicleta datosBicicleta) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelMap modelo = new ModelMap();
        try {
            datosBicicleta.setUsuario(usuario);
            servicioBicicleta.darDeAltaUnaBicicleta(datosBicicleta);
        } catch (BicicletaValidacion e) {
            modelo.put("error", "Debe completar todos los campos");
            return new ModelAndView("registrar-bicicleta", modelo);
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/mis-bicicletas", method = RequestMethod.GET)
    public ModelAndView irAMisBicicletas() {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            if (verificarSiEsPropietario(usuario)) {
                ModelMap modelo = new ModelMap();
                modelo.put("usuario", usuario);
                modelo.put("bicicletas", servicioBicicleta.obtenerBicicletasDelUsuario(usuario));
                return new ModelAndView("mis-bicicletas", modelo);
            }
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/baja-bicicleta/{id}", method = RequestMethod.GET)
    public ModelAndView darDeBajaUnaBicicleta(@PathVariable("id") Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            if (verificarSiEsPropietario(usuario)) {
                servicioBicicleta.darDeBajaUnaBicicleta(id);
            }
            return new ModelAndView("redirect:/mis-bicicletas");
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/bicicletas", method = RequestMethod.GET)
    public ModelAndView verBicicletas() {
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
            List<Bicicleta> bicicletas = servicioBicicleta.obtenerTodasLasBicicleta();
            try {
                if (bicicletas.size() == 0) {
                    modelo.put("error", "No se encontraron Bicicletas");
                } else {
                    modelo.put("bicicletas", bicicletas);
                }
            } catch (Exception e) {
                modelo.put("error", "520");
                return new ModelAndView("error", modelo);
            }
            return new ModelAndView("bicicletas", modelo);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "bicicleta/{id}/detalle", method = RequestMethod.GET)
    public ModelAndView detalleBicicleta(@PathVariable("id") Long id) {
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
            try {
                Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(id);
                List<Resena> resenas = servicioResena.obtenerResenasDeUnaBicicleta(bicicleta);
                modelo.put("usuario", usuario);
                modelo.put("bicicleta", bicicleta);
                modelo.put("resenas", resenas);
                modelo.put("datosAlquiler", new DatosAlquiler());

            } catch (BicicletaNoEncontrada e) {
                return new ModelAndView("pagina-no-encontrada");
            }
            return new ModelAndView("detalle-bicicleta", modelo);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "propietario/{id}/bicicletas", method = RequestMethod.GET)
    public ModelAndView bicicletasDelPropietario(@PathVariable("id") Long id) {
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
            try {
                List<Bicicleta> bicicletas = servicioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(id);
                modelo.put("usuario", usuario);
                modelo.put("bicicletas", bicicletas);
            } catch (Exception e) {
                return new ModelAndView("pagina-no-encontrada");
            }
            return new ModelAndView("bicicletas", modelo);
        }
        return new ModelAndView("redirect:/login");
    }
    
    private boolean verificarSiEsPropietario(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("Propietario");
    }
}
