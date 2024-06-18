package med.volll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        Long id,
        @NotNull
        MotivoCancelamento motivo) {
}
