package med.voll.api.direccion;

import jakarta.validation.constraints.NotBlank;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
//clase inmutable, que contiene solo atributos, constructor y métodos de lectura
public record DatosDireccion(
        @NotBlank
        String calle,
        @NotBlank
        String distrito,
        @NotBlank
        String ciudad,
        @NotBlank
        String numero,
        @NotBlank
        String complemento) {
}
