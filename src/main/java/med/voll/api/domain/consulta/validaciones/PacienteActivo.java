package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Component
public class PacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private PacienteRepository repository;
    public void validar(DatosAgendarConsulta datos){
        if(datos.idPaciente()==null){
            return;
        }

        var pacienteActivo=repository.findActivoById(datos.idPaciente());
        if(!pacienteActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas" +
                    "con pacientes inactivos en el sistema");
        }
    }
}
