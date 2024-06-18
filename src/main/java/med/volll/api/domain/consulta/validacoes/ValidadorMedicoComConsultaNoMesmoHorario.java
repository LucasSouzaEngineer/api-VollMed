package med.volll.api.domain.consulta.validacoes;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.ConsultaRepository;
import med.volll.api.domain.consulta.DadosCadastroConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosCadastroConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndAgendamento(dados.medicoId(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Médico já possui outra consulta nesse mesmo horário");
        }
    }
}
