package med.volll.api.domain.consulta.validacoes;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.DadosCadastroConsulta;
import med.volll.api.domain.medico.MedicoRepository;

public class ValidadorMedicoAtivo {

    private MedicoRepository repository;
    public void validar(DadosCadastroConsulta dados){
        if (dados.medicoId() == null){
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.medicoId());
        if (!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser marcada com médico excllluído");
        }
    }
}
