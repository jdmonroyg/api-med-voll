package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas  {

    public void validar(DatosAgendarConsulta datos){
        var domingo = DayOfWeek.SUNDAY.equals(datos.date().getDayOfWeek());
        var antesDeApertura = datos.date().getHour()<7;
        var despuesDeCierre = datos.date().getHour()>19;
        if (domingo || antesDeApertura || despuesDeCierre){
            throw  new ValidacionDeIntegridad("El horario de atencion de la clinica es de lunes a " +
                    "sabado de 7:00 am a 19:00 horas");
        }
    }
}
