package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository repository;
    public void validar(DatosAgendarConsulta datos){
        var primerHorario=datos.date().withHour(7);
        var ultimoHorario=datos.date().withHour(18);

        var pacienteConConsulta=repository.existsByPacienteIdAndDateBetween(
                datos.idPaciente(),primerHorario,ultimoHorario
        );

        if (pacienteConConsulta){
            throw new ValidacionDeIntegridad("No se permite programar" +
                    " mas de una consulta en el mismo dia por paciente");
        }
    }
}
