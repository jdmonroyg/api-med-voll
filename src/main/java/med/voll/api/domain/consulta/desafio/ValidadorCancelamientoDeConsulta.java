package med.voll.api.domain.consulta.desafio;

import med.voll.api.domain.consulta.DatosCancelamientoConsulta;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
public interface ValidadorCancelamientoDeConsulta {
    public void validar(DatosCancelamientoConsulta datos);
}
