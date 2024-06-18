package med.volll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.volll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco dadosEndereco) {
}
