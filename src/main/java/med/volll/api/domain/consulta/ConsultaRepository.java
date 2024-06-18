package med.volll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Page<Consulta> findByAtivoTrue(Pageable paginacao);

    boolean existsByMedicoIdAndData(Long id, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long aLong, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
