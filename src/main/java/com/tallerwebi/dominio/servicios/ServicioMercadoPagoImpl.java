package com.tallerwebi.dominio.servicios;
import com.google.gson.Gson;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.entidad.Alquiler;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import com.tallerwebi.presentacion.dto.DatosMPResponse;
import com.tallerwebi.presentacion.dto.DatosPago;
import com.tallerwebi.presentacion.dto.DatosPreferencia;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
@Service("ServicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    static {

        MercadoPagoConfig.setAccessToken("APP_USR-5256638928768203-110817-a75a99ea53e7ae7397fa652dfd130e01-1542079310");
    }

    @Override
    public DatosPreferencia crearPreferenciaPago(Alquiler alquiler) throws MPException, MPApiException {
        DatosPago datosPago = new DatosPago(alquiler.getPrecioAlquiler());
        DatosPreferencia responsePago = null;

        String url = "https://api.mercadopago.com/checkout/preferences";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer TEST-5256638928768203-110817-6e7472820e8aa2267acbaecb118b76f0-1542079310");
        headers.set("Content-Type","application/json");

        Gson gson = new Gson();
        String jsonDatos = gson.toJson(datosPago);

        HttpEntity<String> entity = new HttpEntity<String>(jsonDatos, headers);
        DatosMPResponse res = restTemplate.postForObject(url, entity, DatosMPResponse.class);

        if (res != null) {
            responsePago = new DatosPreferencia(res.id, res.payer.name, res.payer.surname, res.payer.phone.area_code + " " + res.payer.phone.number, res.payer.date_created, res.init_point);
        }

        return responsePago;
    }
}