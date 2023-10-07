package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.servicio.ServicioBicicleta;
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
public class ControladorBicicleta {
    private final ServicioBicicleta servicioBicicleta;

    @Autowired
    public ControladorBicicleta(ServicioBicicleta servicioBicicleta) {
        this.servicioBicicleta = servicioBicicleta;
    }

    @ModelAttribute("usuario")
    public Usuario obtenerUsuarioDeSesion(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    @RequestMapping(path = "/registrar-bicicleta", method = RequestMethod.GET)
    public ModelAndView irARegistrarBicicleta(@ModelAttribute("usuario") Usuario usuario) {
        if (verificarSiEsPropietario(usuario)) {
            ModelMap modelo = new ModelMap();
            modelo.put("datosBicicleta", new DatosBicicleta());
            modelo.put("usuario", usuario);
            return new ModelAndView("registrar-bicicleta", modelo);
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/alta-bicicleta", method = RequestMethod.POST)
    public ModelAndView darDeAltaUnaBicicleta(@ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosBicicleta") DatosBicicleta datosBicicleta) {
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
    public ModelAndView irAMisBicicletas(@ModelAttribute("usuario") Usuario usuario) {
        if (verificarSiEsPropietario(usuario)) {
            ModelMap modelo = new ModelMap();
            modelo.put("bicicletas", servicioBicicleta.obtenerBicicletasDelUsuario(usuario));
            return new ModelAndView("mis-bicicletas", modelo);
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/baja-bicicleta/{id}", method = RequestMethod.GET)
    public ModelAndView darDeBajaUnaBicicleta(@PathVariable("id") Long id) {
        servicioBicicleta.darDeBajaUnaBicicleta(id);
        return new ModelAndView("redirect:/mis-bicicletas");
    }

    @RequestMapping(path = "/detalle/{id}", method = RequestMethod.GET)
    public ModelAndView detalleBicicleta(@PathVariable("id") Integer id) {
        Long biciId = id.longValue();
        Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(biciId);

        ModelMap model = new ModelMap();
        model.put("bicicleta", bicicleta);

        return new ModelAndView("detalle-bicicleta", model);
    }

    private boolean verificarSiEsPropietario(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("Propietario");
    }
}
