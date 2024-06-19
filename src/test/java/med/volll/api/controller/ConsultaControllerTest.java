package med.volll.api.controller;

import jakarta.persistence.EntityManager;
import med.volll.api.domain.consulta.Consulta;
import med.volll.api.domain.consulta.ConsultaService;
import med.volll.api.domain.consulta.DadosCadastroConsulta;
import med.volll.api.domain.consulta.DadosDetalheConsulta;
import med.volll.api.domain.endereco.DadosEndereco;
import med.volll.api.domain.medico.DadosCadastroMedico;
import med.volll.api.domain.medico.Especialidade;
import med.volll.api.domain.medico.Medico;
import med.volll.api.domain.paciente.DadosCadastroPaciente;
import med.volll.api.domain.paciente.Paciente;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters

class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroConsulta> dadosCadastroConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalheConsulta> dadosDetalheConsultaJson;

    @MockBean
    private ConsultaService service;


    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
    @WithMockUser
    void cadastrarCenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deveria devolver código http 201 quando informações estão válidas")
    @WithMockUser
    void cadastrarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1L);
        var especialidade = Especialidade.ORTOPEDIA;

        var dadosDetalhamento = new DadosDetalheConsulta(null, 11L, 5L, data);

        when(service.cadastrar(any())).thenReturn((dadosDetalhamento));

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosCadastroConsultaJson.write(
                                        new DadosCadastroConsulta(5L, 11L, data, especialidade)
                                ).getJson()
                                )
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalheConsultaJson.write(
                dadosDetalhamento
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}