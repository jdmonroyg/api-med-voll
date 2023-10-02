package med.voll.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author jdmon on 30/09/2023.
 * @project api-med-voll
 */
public record DatosAutenticacionUsuario(
        @NotBlank (message = "{login.obligatorio}")
        String login,
        @NotBlank (message = "{clave.obligatorio}")
        String clave) {

}
