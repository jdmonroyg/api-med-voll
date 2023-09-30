package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

/**
 * @author jdmon on 29/09/2023.
 * @project api
 */
public record DatosActualizarPaciente(@NotNull Long id, String nombre, String telefono, @Valid DatosDireccion direccion) {
}
