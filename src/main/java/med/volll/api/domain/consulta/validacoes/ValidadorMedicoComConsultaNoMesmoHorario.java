package med.volll.api.domain.consulta.validacoes;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.ConsultaRepository;
import med.volll.api.domain.consulta.DadosCadastroConsulta;

public class ValidadorMedicoComConsultaNoMesmoHorario {

    private ConsultaRepository repository;

    public void validar(DadosCadastroConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.medicoId(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Médico já possui outra consulta nesse mesmo horário");
        }
    }
}
