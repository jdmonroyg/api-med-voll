package med.voll.api.domain.consulta.desafio;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Component ("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta{
    @Autowired
    private ConsultaRepository repository;
    @Override
    public void validar(DatosCancelamientoConsulta datos){
        var consulta= repository.getReferenceById(datos.idConsulta());
        var ahora= LocalDateTime.now();
        var diferenciaEnHoras= Duration.between(ahora,consulta.getDate()).toHours();

        if (diferenciaEnHoras <24){
            throw new ValidacionDeIntegridad("Consulta solamente puede ser cancelada " +
                    "minimo con 24 horas");
        }
    }
}
