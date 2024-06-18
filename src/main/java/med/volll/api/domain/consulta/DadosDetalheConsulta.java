package med.volll.api.domain.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DadosDetalheConsulta(
        Long id,
        String medico,
        String paciente,
        LocalDateTime data
         ) {

    public DadosDetalheConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), consulta.getAgendamento());
    }
}
