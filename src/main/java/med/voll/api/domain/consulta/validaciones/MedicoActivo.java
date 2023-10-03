package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Component
public class MedicoActivo implements ValidadorDeConsultas {
    @Autowired
    private MedicoRepository repository;
    public void validar(DatosAgendarConsulta datos){
        if(datos.idMedico()==null){
            return;
        }

        var medicoActivo=repository.findActivoById(datos.idMedico());
        if(!medicoActivo){
            throw new ValidacionDeIntegridad("No se puede permitir " +
                    "agendar con medicos inactivos en el sistema");
        }
    }
}
