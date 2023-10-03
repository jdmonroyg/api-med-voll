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
public class MedicoConConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository repository;
    public void validar(DatosAgendarConsulta datos){
        if (datos.idMedico()==null){
            return;
        }

        var medicoConConsulta=repository.existsByMedicoIdAndDate(
                datos.idMedico(),datos.date()
        );

        if (medicoConConsulta){
            throw new ValidacionDeIntegridad("Este medico ya tiene" +
                    "una consulta en ese horario");
        }
    }
}
