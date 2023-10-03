package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

}
