package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.bicicleta.*;
import com.tallerwebi.dominio.resenia.Resenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

public class ControladorBicicletaTest {
    private BicicletaController controladorBicicleta;
    private ServicioBicicleta servicioBicicleta;

    @BeforeEach
    public void init(){
        servicioBicicleta = new ServicioBicicletaImp();
    }


    @Test
    public void cuandoUnaBicicletaEsNuevaNoDeberiaTenerResenias() {
        // Preparación
        Bicicleta bici = new Bicicleta(1, Estado.DISPONIBLE);

        servicioBicicleta.agregarBicicleta(bici);

        // Lógica del test
        List<Resenia> cantidadResenias = servicioBicicleta.verReseniasDeBicicleta(bici.getId());

        // Verificación
        assertEquals(0, cantidadResenias.size());
    }

    @Test
    public void cargoUnaReseniaAUnaBicileta() {
        // Preparación
        Bicicleta bici = new Bicicleta(1, Estado.DISPONIBLE);

        servicioBicicleta.agregarBicicleta(bici);

        // Configura el servicio para agregar la reseña
        Resenia resenia = new Resenia(1, "Esta bicicleta es una pija", new Date(), bici.getId());

        // Lógica del test
        boolean agregada = servicioBicicleta.agregarResenia(resenia);
        List<Resenia> reseniasDeBicicleta = servicioBicicleta.verReseniasDeBicicleta(bici.getId());

        // Verificación
        assertTrue(agregada); // Verifica que se haya agregado la reseña con éxito
        assertEquals(1, reseniasDeBicicleta.size()); // Verifica que haya una reseña para la bicicleta
    }

}
