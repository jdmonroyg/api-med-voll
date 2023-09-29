package med.voll.api.direccion;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
@Embeddable
@Getter // propiedades de lombok
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    private String calle;
    private String distrito;
    private String ciudad;
    private String numero;
    private String complemento;

    public Direccion(DatosDireccion direccion) {
        this.calle=direccion.calle();
        this.distrito=direccion.distrito();
        this.ciudad=direccion.ciudad();
        this.numero=direccion.numero();
        this.complemento=direccion.complemento();
    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle=direccion.calle();
        this.distrito=direccion.distrito();
        this.ciudad=direccion.ciudad();
        this.numero=direccion.numero();
        this.complemento=direccion.complemento();
        return this;
    }
}
