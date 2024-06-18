package med.volll.api.domain.consulta;

public record DadosCadastroConsulta(
        Long pacienteId,
        Long medicoId,
        DadosDataAgendamento dadosDataAgendamento) {
}
