package com.tallerwebi.presentacion.controladores;

import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.servicios.DistanciaComparador;
import com.tallerwebi.dominio.servicios.ServicioMapa;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
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

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ControladorMapa {
    private final ServicioMapa servicioMapa;

    @Autowired
    public ControladorMapa(ServicioMapa servicioMapa) {
        this.servicioMapa = servicioMapa;
    }

    @NotNull
    private static List<String> obtenerDistancias(List<Usuario> propietarios, Coordenada coordenada) {
        List<String> distancias = new LinkedList<>();

        for (Usuario propietario : propietarios) {
            Double distanciaEnGrados = Math.sqrt(Math.pow(propietario.getLatitud() - coordenada.getLatitude(), 2) + Math.pow(propietario.getLongitud() - coordenada.getLongitude(), 2));
            Double distanciaEnMetros = distanciaEnGrados * 111.32 * 1000;
            DecimalFormat df = new DecimalFormat("0.0");
            if (distanciaEnMetros < 1000) {
                distancias.add(df.format(distanciaEnMetros) + " metros");
            } else {
                distancias.add(df.format(distanciaEnMetros / 1000) + " km");
            }
        }
        return distancias;
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
        String apiUrl = "https://api.ipdata.co?fields=latitude,longitude&api-key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Coordenada> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Coordenada.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Coordenada coordenada = response.getBody();
            modelo.put("latitudActual", coordenada.getLatitude());
            modelo.put("longitudActual", coordenada.getLongitude());
            List<Usuario> propietarios = servicioMapa.obtenerPropietarios();
            DistanciaComparador comparador = new DistanciaComparador(coordenada.getLatitude(), coordenada.getLongitude());
            propietarios.sort(comparador);
            modelo.put("propietarios", propietarios);
            modelo.put("distancias", obtenerDistancias(propietarios, coordenada));
        } else {
            modelo.put("error", "Hubo un error al llamar a la API");
        }

        return new ModelAndView("mapa", modelo);
    }

}
