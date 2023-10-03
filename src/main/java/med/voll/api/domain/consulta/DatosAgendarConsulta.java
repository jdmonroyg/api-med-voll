package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
public record DatosAgendarConsulta (

        Long id,
        //@JsonAlias({"producto_id", "id_producto"}) se utiliza para asignar un
        //alias a mi variable en caso de que el request venga diferente y poder
        //identificarla, puede ser uno o multiples alias
        @NotNull
        Long idPaciente,
        Long idMedico,
        @NotNull
        @Future
        //@JsonFormat(pattern = "dd/mm/yyyy HH:mm")
        // se utiliza para cambiarle el formato
        LocalDateTime date,
        Especialidad especialidad){
}
