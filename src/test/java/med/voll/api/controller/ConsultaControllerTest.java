package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author jdmon on 3/10/2023.
 * @project api-med-voll
 */
@SpringBootTest // Trabajar con todos los componentes dentro del contexto spring (Simular controller)
@AutoConfigureMockMvc // para poder inyectar MockMvc
@AutoConfigureJsonTesters // encargado de cargar toda la configuracion de json testers
@ActiveProfiles("test")
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc; // para simular un entorno real
    @Autowired //transformacion de java a json
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired //transformacion de json a java
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;

    //Simular la clase de servicio
    @MockBean // para decirle a spring que no use la base de datos real
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("Deberia retonar estado http 400 cuando los datos sean invalidos")
    @WithMockUser // spring hace la simulacion de un usuario en db
    void agendarEscenario1() throws Exception {
        //given //when
        var response=mvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("deberia retornar estado http 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
        var date = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        //simulamos la consulta
        var datos = new DatosDetalleConsulta(null, 3L, 5L,date);

        // when

        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);
        //aca especificamos un dato simulado 'datos'
        var response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(null,2L,5L,date, especialidad)).getJson())
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalleConsultaJacksonTester.write(datos).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}