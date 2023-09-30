package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.Direccion;

/**
 * @author jdmon on 29/09/2023.
 * @project api-med-voll
 */
public record DatosRespuestaMedico(Long id, String nombre, String email,
        String telefono, String documento,
        Direccion direccion) {

    public DatosRespuestaMedico(Medico medico){
        this(medico.getId(),medico.getNombre(),medico.getEmail(),
                medico.getTelefono(),medico.getDocumento(),medico.getDireccion());
    }
}
