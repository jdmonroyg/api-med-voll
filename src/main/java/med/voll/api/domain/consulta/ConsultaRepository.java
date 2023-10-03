package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

    Boolean existsByPacienteIdAndDateBetween(Long aLong, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndDate(Long aLong, LocalDateTime date);
}
