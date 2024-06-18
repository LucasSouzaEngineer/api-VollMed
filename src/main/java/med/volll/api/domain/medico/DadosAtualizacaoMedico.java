package med.volll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.volll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
