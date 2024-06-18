package med.volll.api.domain.consulta.validacoes;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.DadosCadastroConsulta;
import med.volll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosCadastroConsulta dados){
        var pacienteEstaAtivo = repository.findAtivoById(dados.pacienteId());
        if (!pacienteEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
