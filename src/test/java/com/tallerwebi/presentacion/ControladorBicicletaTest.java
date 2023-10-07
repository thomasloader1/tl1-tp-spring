package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.bicicleta.*;
import com.tallerwebi.dominio.resenia.Resenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

public class ControladorBicicletaTest {
    private BicicletaController controladorBicicleta;
    private ServicioBicicleta servicioBicicleta;

    @BeforeEach
    public void init(){
        servicioBicicleta = mock(ServicioBicicleta.class);
        controladorBicicleta = new BicicletaController(servicioBicicleta);
    }

    @Test
    public void cuandoAccedoAlInicioDeBicicletasMuestraBienvenida() {
        ModelAndView mav = controladorBicicleta.index();

        // Verificacion (Vista y mensaje)
        assertThat(mav.getViewName(),equalToIgnoringCase("bicicletas"));
        assertThat(mav.getModel().get("message"),equalTo("Bienvenido"));
    }

    @Test
    public void cuandoAccedoAlInicioDeBicicletasYNoHayBicicletasLaListaEstaVacia() {
        List<Bicicleta> bicicletasMock = new ArrayList<Bicicleta>();

        when(servicioBicicleta.getBicicletas()).thenReturn(bicicletasMock);

        ModelAndView mav = controladorBicicleta.index();
        List<Bicicleta> bicicletas = (List<Bicicleta>) mav.getModel().get("bicicletas");

        // Verificacion (Vista y mensaje)
        assertThat(mav.getViewName(),equalToIgnoringCase("bicicletas"));
        assertThat(mav.getModel().get("message"),equalTo("Bienvenido"));
        assertThat(bicicletas.size(),comparesEqualTo(0));

    }

    @Test
    public void cuandoAccedoAlInicioDeBicicletasYNoHayBicicletasLaListaEstaVacia() {
        List<Bicicleta> bicicletasMock = new ArrayList<Bicicleta>();

        when(servicioBicicleta.getBicicletas()).thenReturn(bicicletasMock);

        ModelAndView mav = controladorBicicleta.index();
        List<Bicicleta> bicicletas = (List<Bicicleta>) mav.getModel().get("bicicletas");

        // Verificacion (Vista y mensaje)
        assertThat(mav.getViewName(),equalToIgnoringCase("bicicletas"));
        assertThat(mav.getModel().get("message"),equalTo("Bienvenido"));
        assertThat(bicicletas.size(),comparesEqualTo(0));

    }


    @Test
    public void cuandoUnaBicicletaEsNuevaNoDeberiaTenerResenias() {
        // Preparación
        Bicicleta bici = new Bicicleta(1, Estado.DISPONIBLE);

       // servicioBicicleta.agregarBicicleta(bici);

        when(servicioBicicleta.getBicicleta()).thenReturn(bici);

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
