package com.tallerwebi.presentacion.controladores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioSinRol;
import com.tallerwebi.dominio.servicios.ServicioBicicleta;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.presentacion.dto.DatosLogin;
import com.tallerwebi.presentacion.dto.DatosUsuario;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class ControladorLogin {
    private final ServicioLogin servicioLogin;
    private final ServicioBicicleta servicioBicicleta;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin, ServicioBicicleta servicioBicicleta) {
        this.servicioLogin = servicioLogin;
        this.servicioBicicleta = servicioBicicleta;
    }

    @NotNull
    private static String getApiUrl(DatosUsuario datosUsuario) {
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("GEOAPIFY_API_KEY");
        String apiUrl = "https://api.geoapify.com/v1/geocode/search?apiKey=" + apiKey;

        if (!datosUsuario.getDireccion().isEmpty()) {
            apiUrl += "&text=" + datosUsuario.getDireccion();
        }

        if (!datosUsuario.getCiudad().isEmpty()) {
            apiUrl += "&city=" + datosUsuario.getCiudad();
        }

        if (!datosUsuario.getProvincia().isEmpty()) {
            apiUrl += "&state=" + datosUsuario.getProvincia();
        }

        if (!datosUsuario.getCodigoPostal().isEmpty()) {
            apiUrl += "&postcode=" + datosUsuario.getCodigoPostal();
        }

        if (!datosUsuario.getPais().isEmpty()) {
            apiUrl += "&country=" + datosUsuario.getPais();
        }
        return apiUrl;
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
    public ModelAndView registrarme(@ModelAttribute("datosUsuario") DatosUsuario datosUsuario) {
        ModelMap model = new ModelMap();
        try {
            Coordenada coordenada = obtenerCoordenadas(datosUsuario);
            datosUsuario.setLatitud(coordenada.getLatitude());
            datosUsuario.setLongitud(coordenada.getLongitude());
            servicioLogin.registrar(datosUsuario);
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
        model.put("datosUsuario", new DatosUsuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelMap model = new ModelMap();
        List<Bicicleta> bicicletas = servicioBicicleta.obtenerTodasLasBicicletasDisponibles();

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        model.put("bicicletas" , bicicletas);
        model.put("usuario", usuario);
        return new ModelAndView("home", model);
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

    private Coordenada obtenerCoordenadas(DatosUsuario datosUsuario) throws JsonProcessingException {
        String apiUrl = getApiUrl(datosUsuario);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        JsonNode features = jsonNode.get("features");
        JsonNode firstFeature = features.get(0);
        JsonNode geometry = firstFeature.get("geometry");

        if (geometry != null && geometry.has("coordinates")) {
            JsonNode coordinates = geometry.get("coordinates");
            if (coordinates.isArray() && coordinates.size() == 2) {
                Double latitude = coordinates.get(1).asDouble();
                Double longitude = coordinates.get(0).asDouble();

                Coordenada coordenada = new Coordenada();
                coordenada.setLatitude(latitude);
                coordenada.setLongitude(longitude);

                return coordenada;
            }
        }
        return null;
    }
}