package med.volll.api.domain.consulta.validacoes;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.ConsultaRepository;
import med.volll.api.domain.consulta.DadosCadastroConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosCadastroConsulta dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndAgendamentoBetween(dados.pacienteId(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
