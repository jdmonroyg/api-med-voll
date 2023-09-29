package med.voll.api.paciente;

/**
 * @author jdmon on 29/09/2023.
 * @project api
 */
public record DatosListadoPaciente(Long id,String nombre, String email, String documento) {
    public DatosListadoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(),paciente.getEmail(),paciente.getDocumento());
    }

}
