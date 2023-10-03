package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
public interface MedicoRepository extends JpaRepository<Medico,Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    //Se usa con una @Query para consultar con una query personalizada
    @Query("""
        select m from Medico m
        where m.activo=true
        and
        m.especialidad=:especialidad
        and
        m.id not in(
            select c.medico.id from Consulta c
            where
            c.date=:date
        )
        order by rand()
        limit 1
        """)
    Medico SeleccionarMedicoConEspecilidadEnFecha(Especialidad especialidad, LocalDateTime date);
}
