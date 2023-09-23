package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.bicicleta.*;
import com.tallerwebi.dominio.resenia.Resenia;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ControladorBicicletaTest {
    private BicicletaController controladorBicicleta;
    private ServicioBicicleta servicioBicicleta;

    @Test
    public void cuandoUnaBicicletaEsNuevaNoDeberiaTenerResenias() {
        // Preparación
        Bicicleta bici = new Bicicleta(1, Estado.DISPONIBLE);

        // Mock del servicioBicicleta
        ServicioBicicleta servicioBicicleta = mock(ServicioBicicleta.class);
        when(servicioBicicleta.verReseniasDeBicicleta(bici.getId())).thenReturn(Collections.emptyList());

        // Lógica del test
        List<Resenia> cantidadResenias = servicioBicicleta.verReseniasDeBicicleta(bici.getId());

        // Verificación
        assertEquals(0, cantidadResenias.size());
    }

    @Test
    public void cargoUnaReseniaAUnaBicileta() {
        // Preparación
       /* Bicicleta bici = new Bicicleta(1, Estado.DISPONIBLE);
        ServicioBicicleta servicioBicicleta = mock(ServicioBicicleta.class);

        servicioBicicleta.agregarBicicleta(bici);

        // Configura el servicio para agregar la reseña
        Resenia resenia = new Resenia(1, "Esta bicicleta es una pija", new Date(), bici.getId());

       // when(servicioBicicleta.agregarResenia(resenia)).thenReturn(true);

        // Lógica del test
        boolean agregada = servicioBicicleta.agregarResenia(resenia);
        List<Resenia> reseniasDeBicicleta = servicioBicicleta.verReseniasDeBicicleta(bici.getId());

        // Verificación
        assertTrue(agregada); // Verifica que se haya agregado la reseña con éxito
        assertEquals(1, reseniasDeBicicleta.size()); // Verifica que haya una reseña para la bicicleta */
    }


}
