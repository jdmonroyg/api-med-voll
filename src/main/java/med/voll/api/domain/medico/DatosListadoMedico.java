package med.voll.api.domain.medico;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
public record DatosListadoMedico(Long id,String nombre, String especialidad,
    String documento, String email) {
    public DatosListadoMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(),medico.getEspecialidad().toString(),
                medico.getDocumento(), medico.getEmail());
    }
}
