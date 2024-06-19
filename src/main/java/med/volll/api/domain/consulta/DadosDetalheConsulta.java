package med.volll.api.domain.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DadosDetalheConsulta(
        Long id,
        Long medico,
        Long paciente,
        LocalDateTime data
         ) {

    public DadosDetalheConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getAgendamento());
    }
}
