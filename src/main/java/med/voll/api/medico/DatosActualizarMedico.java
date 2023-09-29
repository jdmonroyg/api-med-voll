package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.direccion.DatosDireccion;

/**
 * @author jdmon on 29/09/2023.
 * @project api
 */
public record DatosActualizarMedico(@NotNull Long id, String nombre, String documento, @Valid DatosDireccion direccion) {


}
