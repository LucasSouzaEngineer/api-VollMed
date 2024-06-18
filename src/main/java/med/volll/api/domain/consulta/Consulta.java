package med.volll.api.domain.consulta;

import jakarta.persistence.*;
import med.volll.api.domain.medico.Medico;
import med.volll.api.domain.paciente.Paciente;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Medico medico;
    private LocalDateTime agendamento;
    private boolean ativo;
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    public Consulta(){}
    public Consulta(Paciente paciente, Medico medico, LocalDateTime agendamento){
        this.paciente = paciente;
        this.medico = medico;
        this.agendamento = agendamento;

        this.ativo = true;
        this.motivoCancelamento = null;

        this.medico.setConsulta(this);
        this.paciente.setConsulta(this);
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }



    public LocalDateTime getAgendamento() {
        return agendamento;
    }

    public void cancelar(MotivoCancelamento motivo) {
        this.ativo = false;
        this.motivoCancelamento = motivo;
        System.out.println(this.motivoCancelamento);
    }
}
