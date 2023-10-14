package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.BicicletaNoEncontrada;
import com.tallerwebi.dominio.excepcion.BicicletaValidacion;
import com.tallerwebi.dominio.servicios.ServicioResena;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
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
    @Autowired
    public ControladorBicicleta(ServicioBicicleta servicioBicicleta, ServicioResena servicioResena) {
        this.servicioBicicleta = servicioBicicleta;
        this.servicioResena = servicioResena;
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
            modelo.put("rol", usuario.getRol());
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
            modelo.put("rol", usuario.getRol());
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

    @RequestMapping(path = "bicicleta/detalle/{id}", method = RequestMethod.GET)
    public ModelAndView detalleBicicleta(@PathVariable("id") Integer id) throws BicicletaNoEncontrada {
        Long biciId = id.longValue();
        ModelMap model = new ModelMap();
        try {
            Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(biciId);
            List<Resena> resenas = servicioResena.obtenerResenasDeUnaBicicleta(bicicleta);
            model.put("bicicleta", bicicleta);
            model.put("resenas",resenas);
            model.put("datosAlquiler", new DatosAlquiler());
          
        } catch (BicicletaNoEncontrada e) {
            return new ModelAndView("pagina-no-encontrada");
        }
        return new ModelAndView("detalle-bicicleta", model);
    }

    private boolean verificarSiEsPropietario(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("Propietario");
    }
}
