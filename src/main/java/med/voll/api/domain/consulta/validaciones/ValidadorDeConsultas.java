package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */

public interface ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta consulta);
}
