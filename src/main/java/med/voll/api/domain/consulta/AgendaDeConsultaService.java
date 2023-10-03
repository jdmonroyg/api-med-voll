package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.desafio.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    //para que spring automaticamente agrega la lista de validaciones
    // sin necesidad de estar agregando una por una
    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

    public AgendaDeConsultaService() {
    }

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){
        //para verificar si el paciente fue encontrado
        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            System.out.println("Id encontrado");
        }else {
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if (datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        validadores.forEach(validador -> validador.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente())
                .orElse(null);
        var medico = seleccionarMedico(datos);
        if (medico==null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario" +
                    "y especialidad");
        }
        var consulta= new Consulta(medico,paciente,datos.date());
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
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

    public void cancelar(DatosCancelamientoConsulta datos){
        if (consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("Id de la consulta informado" +
                    " no existe!");
        }
        validadoresCancelamiento.forEach(v->v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivos());
    }
}
