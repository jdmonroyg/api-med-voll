package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
public record DatosDetalleConsulta (Long id, Long idPaciente, Long idMedico, LocalDateTime date) {
    public DatosDetalleConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getPaciente().getId(),
                consulta.getMedico().getId(),consulta.getDate());
    }
}
