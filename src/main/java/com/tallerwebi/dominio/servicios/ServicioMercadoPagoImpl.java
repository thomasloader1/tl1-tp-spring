package com.tallerwebi.dominio.servicios;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.presentacion.dto.DatosAlquiler;
import org.springframework.stereotype.Service;

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
    public Preference crearPreferenciaPago(DatosAlquiler datosAlquiler) throws MPException, MPApiException {

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id("1234")
                        .title("Games")
                        .description("PS5")
                        .pictureUrl("http://picture.com/PS5")
                        .categoryId("games")
                        .quantity(2)
                        .currencyId("BRL")
                        .unitPrice(new BigDecimal("4000"))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();
        PreferenceClient client = new PreferenceClient();

        Preference preference = client.create(preferenceRequest);

        return preference;
    }


//        try {
//        PreferenceItemRequest itemRequest =
//                PreferenceItemRequest.builder()
//                        .title("Alquiler de Bicicleta")
//                        .description("Alquiler de bicicleta por " + datosAlquiler.getCantidadHoras() + " horas")
//                        .quantity(datosAlquiler.getCantidadHoras())
//                        .currencyId("ARS")
//                        .unitPrice(new BigDecimal(datosAlquiler.getPrecioAlquiler()))
//                        .build();
//
//        List<PreferenceItemRequest> items = new ArrayList<>();
//        items.add(itemRequest);
//
//        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
//                .items(items).build();
//
//            PreferenceClient client = new PreferenceClient();
//            Preference preference = client.create(preferenceRequest);
//            return preference;
//        } catch (MPException e) {
//
//            e.printStackTrace();
//            throw e;
//        }

}