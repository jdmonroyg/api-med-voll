package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Service
public class AgendaDeConsultaService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    public void agendar(DatosAgendarConsulta datos){
        //para verificar si el paciente fue encontrado
        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            System.out.println("Id encontrado");
        }else {
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if (datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente())
                .orElse(null);
        var medico = seleccionarMedico(datos);
        var consulta= new Consulta(null,medico,paciente,datos.date());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad()==null){
            throw new ValidacionDeIntegridad(
                    "Debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.SeleccionarMedicoConEspecilidadEnFecha(datos.especialidad(),
                datos.date());
    }
}
