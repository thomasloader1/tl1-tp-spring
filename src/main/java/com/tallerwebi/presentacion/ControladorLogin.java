package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioBicicleta;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorLogin {
    private final ServicioLogin servicioLogin;
    private final ServicioBicicleta servicioBicicleta;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin,ServicioBicicleta servicioBicicleta) {
        this.servicioLogin = servicioLogin;
        this.servicioBicicleta = servicioBicicleta;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return new ModelAndView("redirect:/home");
        }
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerTodasLasBicicleta();
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("usuario", usuarioBuscado);
            request.getSession().setAttribute("bicicletas", bicicletas);
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (UsuarioSinRol e) {
            model.put("error", "Tenés que seleccionar tu tipo de usuario");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return new ModelAndView("redirect:/home");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Bicicleta> bicicletas = (List<Bicicleta>) session.getAttribute("bicicletas");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("rol", usuario.getRol());
        modelAndView.addObject("bicicletas", bicicletas);

        return modelAndView;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/cerrar-sesion", method = RequestMethod.POST)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}