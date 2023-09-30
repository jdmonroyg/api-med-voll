package med.voll.api.paciente;

import med.voll.api.direccion.DatosDireccion;
import med.voll.api.direccion.Direccion;

/**
 * @author jdmon on 29/09/2023.
 * @project api-med-voll
 */
public record DatosRespuestaPaciente(Long id, String nombre, String email,
                                     String telefono, String documento,
                                     Direccion direccion) {
    public DatosRespuestaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(), paciente.getDocumento(),
                paciente.getDireccion());
    }
}
