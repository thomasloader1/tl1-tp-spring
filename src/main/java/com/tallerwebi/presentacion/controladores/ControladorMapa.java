package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import io.github.cdimascio.dotenv.Dotenv;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorMapa {
    private final ServicioMapa servicioMapa;

    @Autowired
    public ControladorMapa(ServicioMapa servicioMapa) {
        this.servicioMapa = servicioMapa;
    }


    @ModelAttribute("usuario")
    public Usuario obtenerUsuarioDeSesion(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    @RequestMapping(path = "/mapa", method = RequestMethod.GET)
    public ModelAndView irAMapa() {
        ModelMap modelo = new ModelMap();
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("IPDATA_API_KEY");
        String apiUrl = "https://api.ipdata.co?api-key=" + apiKey + "&fields=latitude,longitude";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Coordenada> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Coordenada.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Coordenada coordenada = response.getBody();
            modelo.put("latitudActual", coordenada.getLatitude());
            modelo.put("longitudActual", coordenada.getLongitude());
        } else {
            modelo.put("error", "Hubo un error al llamar a la API");
        }
        modelo.put("propietarios", servicioMapa.obtenerPropietarios());
        return new ModelAndView("mapa", modelo);
    }
}
