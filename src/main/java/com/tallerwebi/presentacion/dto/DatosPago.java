package com.tallerwebi.presentacion.dto;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatosPago {

    private List<Item> items;
    private BackUrl back_urls;
    private String auto_return;
    private String external_reference;

    public DatosPago(){
    }
    public DatosPago (Number monto){
        items = new ArrayList<Item>();
        Item item = new Item();
        item.title = "Alquiler bici";
        item.quantity = 1;
        item.unit_price = monto;
        items.add(item);
        back_urls = new BackUrl();
        back_urls.success = "http://localhost:8080/validar-pago";
        back_urls.pending = "http://localhost:8080/validar-pago";
        back_urls.failure = "http://localhost:8080/validar-pago";
        auto_return = "all";
       // external_reference = pack;

    }

    private static class Item {
        private String title;
        private Number quantity;
        private Number unit_price;
    }

    private static class BackUrl {
        private String success;
        private String pending;
        private String failure;
    }
}
