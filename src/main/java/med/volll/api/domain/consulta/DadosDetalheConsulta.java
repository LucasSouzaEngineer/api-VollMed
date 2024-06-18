package med.volll.api.domain.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DadosDetalheConsulta(
        Long id,
        String medico,
        String paciente,
        LocalDate data,
        LocalTime horario
         ) {

    public DadosDetalheConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), getData(consulta.getAgendamento()), getHorario(consulta.getAgendamento()));
    }

    private static LocalTime getHorario(LocalDateTime agendamento) {
        return LocalTime.from(agendamento);
    }

    private static LocalDate getData(LocalDateTime agendamento) {
        return LocalDate.from(agendamento);
    }
}
