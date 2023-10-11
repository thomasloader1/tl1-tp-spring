package com.tallerwebi.integracion;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.config.HibernateTestConfig;
import com.tallerwebi.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorLoginTest {

    private Usuario usuarioMock;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


/*
	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALaRaiz() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
		assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
		assertThat(true, is(modelAndView.getModel().isEmpty()));
	}

	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("datosLogin").toString(),  containsString("com.tallerwebi.presentacion.dto.DatosLogin"));

	}
	*/
}
