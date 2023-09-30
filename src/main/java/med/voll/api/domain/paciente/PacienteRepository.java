package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    Page<Paciente> findByActivoTrue(Pageable paginacion);
}
