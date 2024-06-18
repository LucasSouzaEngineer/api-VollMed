package med.volll.api.domain.consulta;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.medico.Medico;
import med.volll.api.domain.medico.MedicoRepository;
import med.volll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public Consulta cadastrar(DadosCadastroConsulta dados) {
        if (!pacienteRepository.existsById(dados.pacienteId())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dados.medicoId()!= null && !medicoRepository.existsById(dados.medicoId())){
            throw new ValidacaoException("Id do médico informado não existe");
        }

        var medico = escolherMedico(dados);
        var paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        LocalDateTime dataConsulta = dados.data();
        if (medico.verificar(dataConsulta) && paciente.verificar(dataConsulta) && verificarPrazoAgendamento(dataConsulta)){
            var consulta = new Consulta(paciente, medico, dataConsulta);
            consultaRepository.save(consulta);
            return consulta;
        }
        throw new RuntimeException("Não foi possível cadastrar consulta");
    }

    private Medico escolherMedico(DadosCadastroConsulta dados) {
        if (dados.medicoId() != null){
            return medicoRepository.getReferenceById(dados.medicoId());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(dados.id());
        if (consultaOptional.isPresent()){
            Consulta consulta = consultaOptional.get();
            if (verificarPrazoCancelamento(consulta.getAgendamento())){
                System.out.println("validado");
                consulta.cancelar(dados.motivo());
            }else {
                System.out.println("Fora do prazo de cancelamento");
            }
        }else{
            System.out.println("Consulta não encontrada");
        }
    }

//    private LocalDateTime gerarDataConsulta(DadosDataAgendamento dados){
//        return LocalDateTime.of(dados.ano(), dados.mes(), dados.dia(), dados.hora(),dados.minuto());
//    }

    private boolean verificarPrazoAgendamento(LocalDateTime dataConsulta) {
        LocalDateTime agora = LocalDateTime.now();
        int domingo = 7;
        LocalTime abertura = LocalTime.of(7,0);
        LocalTime fechamento = LocalTime.of(19, 0);
        boolean prazoAgendamento = agora.isBefore(dataConsulta.minusMinutes(30l));
        boolean diaAgendamento = !(dataConsulta.getDayOfWeek().getValue() == domingo);
        boolean horaAgendamento = LocalTime.from(dataConsulta).isAfter(abertura) && LocalTime.from(dataConsulta).isBefore(fechamento);
        return prazoAgendamento && diaAgendamento && horaAgendamento;
    }

    private boolean verificarPrazoCancelamento(LocalDateTime dataConsulta){
        LocalDateTime agora = LocalDateTime.now();
        System.out.println("validado");
        return agora.isBefore(dataConsulta.minusHours(24l));
    }

//    public ConsultaRepository getConsultaRepository() {
//        return consultaRepository;
//    }

    public Page<DadosDetalheConsulta> listar(Pageable paginacao) {
        return consultaRepository.findByAtivoTrue(paginacao)
                .map(DadosDetalheConsulta::new);
    }
}
