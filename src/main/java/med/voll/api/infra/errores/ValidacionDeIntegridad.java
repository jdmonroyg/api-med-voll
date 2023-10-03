package med.voll.api.infra.errores;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
public class ValidacionDeIntegridad extends RuntimeException {
    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
