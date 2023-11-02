package com.tallerwebi.presentacion.controladores;

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
import java.util.ArrayList;
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
            modelo.put("bicicletasUso" , servicioBicicleta.obtenerBicicletasEnUsoPorIdUsuario(usuario.getId()));
            modelo.put("bicicletasRotas" , servicioBicicleta.obtenerBicicletasEnReparacionPorIdUsuario(usuario.getId()));
            modelo.put("bicicletas", servicioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(usuario.getId()));
            return new ModelAndView("mis-bicicletas", modelo);
        }
        return new ModelAndView("redirect:/home");
    }


    @RequestMapping(path = "/baja-bicicleta/{id}", method = RequestMethod.GET)
    public ModelAndView darDeBajaUnaBicicleta(@PathVariable("id") Long id) {
        servicioBicicleta.darDeBajaUnaBicicleta(id);
        return new ModelAndView("redirect:/mis-bicicletas");
    }

    @RequestMapping(path = "/bicicletas", method = RequestMethod.GET)
    public ModelAndView verBicicletas(){

        ModelMap model = new ModelMap();
        List <Bicicleta> bicis = servicioBicicleta.obtenerTodasLasBicicleta();

        try{
            if(bicis.size() == 0){
                model.put("error", "No se encontraron Bicicletas");
            }else {
                model.put("bicicletas", bicis);
            }
        }catch (Exception e){
            model.put("error", "520");
            return new ModelAndView("error", model);
        }

        return  new ModelAndView("bicicletas", model);
    }

    @RequestMapping(path = "bicicleta/detalle/{id}", method = RequestMethod.GET)
    public ModelAndView detalleBicicleta(@PathVariable("id") Integer id) throws BicicletaNoEncontrada {
        Long biciId = id.longValue();

        ModelMap model = new ModelMap();
        List <Bicicleta> bicis = servicioBicicleta.obtenerTodasLasBicicleta();

        try{
            if(bicis.size() == 0){
                model.put("error", "No se encontraron Bicicletas");
            }else {
                model.put("bicicletas", bicis);
            }
        }catch (Exception e){
            model.put("error", "520");
            return new ModelAndView("error", model);
        }

        return  new ModelAndView("bicicletas", model);
    }

    @RequestMapping(path = "bicicleta/{id}/detalle", method = RequestMethod.GET)
    public ModelAndView detalleBicicleta(@ModelAttribute("usuario") Usuario usuario, @PathVariable("id") Integer id) throws BicicletaNoEncontrada {
        if (usuario != null) {
            Long biciId = id.longValue();
            ModelMap model = new ModelMap();
            try {
                Bicicleta bicicleta = servicioBicicleta.obtenerBicicletaPorId(biciId);
                List<Resena> resenas = servicioResena.obtenerResenasDeUnaBicicleta(bicicleta);
                model.put("bicicleta", bicicleta);
                model.put("resenas", resenas);
                model.put("datosAlquiler", new DatosAlquiler());

            } catch (BicicletaNoEncontrada e) {
                return new ModelAndView("pagina-no-encontrada");
            }
            return new ModelAndView("detalle-bicicleta", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "propietario/{id}/bicicletas", method = RequestMethod.GET)
    public ModelAndView bicicletasDelPropietario(@ModelAttribute("usuario") Usuario usuario, @PathVariable("id") Long id) {
        if (usuario != null) {
            ModelMap model = new ModelMap();
            try {
                List<Bicicleta> bicicletas = servicioBicicleta.obtenerBicicletasDisponiblesPorIdUsuario(id);
                model.put("bicicletas", bicicletas);
            } catch (Exception e) {
                return new ModelAndView("pagina-no-encontrada");
            }
            return new ModelAndView("bicicletas", model);
        }
        return new ModelAndView("redirect:/login");
    }

    private boolean verificarSiEsPropietario(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("Propietario");
    }
    private boolean verificarSiEsCliente(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("Cliente");
    }
}
