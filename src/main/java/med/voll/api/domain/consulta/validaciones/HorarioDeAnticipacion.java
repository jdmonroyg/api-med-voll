package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var horaDeConsulta = datos.date();
        var diferenciaDe30Min = Duration.between(ahora,horaDeConsulta)
                .toMinutes()<30;

        if (diferenciaDe30Min){
            throw  new ValidacionDeIntegridad("Las consultas deben programarse" +
                    "con al menos 30 minutos de anticipacion");
        }
    }

}
