package med.volll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.volll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosCadastroConsulta(
        @NotNull
        Long pacienteId,

        Long medicoId,
        @NotNull
        @Future
        LocalDateTime data,
        Especialidade especialidade) {
}
