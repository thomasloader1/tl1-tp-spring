package com.tallerwebi.dominio.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.entidad.Coordenada;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.infraestructura.repositorios.RepositorioMapa;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@Service("servicioMapa")
@Transactional
public class ServicioMapaImpl implements ServicioMapa {
    private final RepositorioMapa repositorioMapa;

    @Autowired
    public ServicioMapaImpl(RepositorioMapa repositorioMapa) {
        this.repositorioMapa = repositorioMapa;
    }

    @Override
    public List<Usuario> obtenerPropietarios() {
        return repositorioMapa.obtenerPropietarios();
    }

    @Override
    public List<String> obtenerDistancias(List<Usuario> propietarios) {
        Coordenada coordenada = obtenerUbicacionActual();
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

    @Override
    public Coordenada obtenerUbicacionActual() {
        Dotenv dotenv = Dotenv.configure().load();
        String ipDataApiUrl = dotenv.get("IPDATA_API_URL");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Coordenada> response = restTemplate.exchange(ipDataApiUrl, HttpMethod.GET, null, Coordenada.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<String> obtenerTiempoDeLlegadaCaminando(List<Usuario> propietarios) throws JsonProcessingException {
        Dotenv dotenv = Dotenv.configure().load();
        String googleMapsApiUrl = dotenv.get("GOOGLE_MAPS_API_URL");
        Coordenada coordenada = obtenerUbicacionActual();
        RestTemplate restTemplate = new RestTemplate();
        List<String> tiempos = new LinkedList<>();
        for (Usuario propietario : propietarios) {
            ResponseEntity<String> distanceMatrixResponse = restTemplate.exchange(googleMapsApiUrl + "&origins=" + coordenada.getLatitude() + "," + coordenada.getLongitude() + "&destinations=" + propietario.getLatitud() + "," + propietario.getLongitud() + "&mode=walking", HttpMethod.GET, null, String.class);
            agregarTiempoDeLlegada(tiempos, distanceMatrixResponse);
        }

        return tiempos;
    }

    @Override
    public List<String> obtenerTiempoDeLlegadaEnBicicleta(List<Usuario> propietarios) throws JsonProcessingException {
        Dotenv dotenv = Dotenv.configure().load();
        String googleMapsApiUrl = dotenv.get("GOOGLE_MAPS_API_URL");
        Coordenada coordenada = obtenerUbicacionActual();
        RestTemplate restTemplate = new RestTemplate();
        List<String> tiempos = new LinkedList<>();
        for (Usuario propietario : propietarios) {
            ResponseEntity<String> distanceMatrixResponse = restTemplate.exchange(googleMapsApiUrl + "&origins=" + coordenada.getLatitude() + "," + coordenada.getLongitude() + "&destinations=" + propietario.getLatitud() + "," + propietario.getLongitud() + "&mode=bicycling", HttpMethod.GET, null, String.class);
            agregarTiempoDeLlegada(tiempos, distanceMatrixResponse);
        }

        return tiempos;
    }

    @Override
    public List<String> obtenerTiempoDeLlegadaEnAuto(List<Usuario> propietarios) throws JsonProcessingException {
        Dotenv dotenv = Dotenv.configure().load();
        String googleMapsApiUrl = dotenv.get("GOOGLE_MAPS_API_URL");
        Coordenada coordenada = obtenerUbicacionActual();
        RestTemplate restTemplate = new RestTemplate();
        List<String> tiempos = new LinkedList<>();
        for (Usuario propietario : propietarios) {
            ResponseEntity<String> distanceMatrixResponse = restTemplate.exchange(googleMapsApiUrl + "&origins=" + coordenada.getLatitude() + "," + coordenada.getLongitude() + "&destinations=" + propietario.getLatitud() + "," + propietario.getLongitud() + "&mode=driving", HttpMethod.GET, null, String.class);
            agregarTiempoDeLlegada(tiempos, distanceMatrixResponse);
        }

        return tiempos;
    }

    private void agregarTiempoDeLlegada(List<String> tiempos, ResponseEntity<String> distanceMatrixResponse) throws JsonProcessingException {
        if (distanceMatrixResponse.getStatusCode() == HttpStatus.OK) {
            String response = distanceMatrixResponse.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            JsonNode duration = jsonResponse.get("rows").get(0).get("elements").get(0).get("duration");
            String tiempo = duration.get("text").asText();
            tiempos.add(tiempo.replaceAll("hours?\\b", "h"));
        }
    }
}
