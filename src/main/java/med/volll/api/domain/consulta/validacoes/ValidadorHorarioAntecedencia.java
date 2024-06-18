package med.volll.api.domain.consulta.validacoes;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.DadosCadastroConsulta;

import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia {

    public void validar(DadosCadastroConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();

        var foraDoPrazo = agora.plusMinutes(30).isAfter(dataConsulta);

        if (foraDoPrazo){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
