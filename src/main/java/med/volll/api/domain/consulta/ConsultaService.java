package med.volll.api.domain.consulta;

import med.volll.api.domain.ValidacaoException;
import med.volll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.volll.api.domain.medico.Medico;
import med.volll.api.domain.medico.MedicoRepository;
import med.volll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalheConsulta cadastrar(DadosCadastroConsulta dados) {
        if (!pacienteRepository.existsById(dados.pacienteId())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dados.medicoId()!= null && !medicoRepository.existsById(dados.medicoId())){
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }
        var paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        var consulta = new Consulta(paciente, medico, dados.data());
        consultaRepository.save(consulta);
        return new DadosDetalheConsulta(consulta);
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

    private boolean verificarPrazoCancelamento(LocalDateTime dataConsulta){
        LocalDateTime agora = LocalDateTime.now();
        System.out.println("validado");
        return agora.isBefore(dataConsulta.minusHours(24l));
    }

    public Page<DadosDetalheConsulta> listar(Pageable paginacao) {
        return consultaRepository.findByAtivoTrue(paginacao)
                .map(DadosDetalheConsulta::new);
    }
}
