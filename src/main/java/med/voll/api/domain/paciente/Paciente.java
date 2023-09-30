package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
@Entity
@Table (name = "pacientes")
@Getter // propiedades de lombok
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private boolean activo;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.activo=true;
        this.nombre=datosRegistroPaciente.nombre();
        this.email=datosRegistroPaciente.email();
        this.documento=datosRegistroPaciente.documento();
        this.telefono=datosRegistroPaciente.telefono();
        this.direccion=new Direccion(datosRegistroPaciente.direccion());
    }



    public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente) {
        if (datosActualizarPaciente.nombre()!=null){
            this.nombre= datosActualizarPaciente.nombre();
        }
        if (datosActualizarPaciente.telefono()!=null) {
            this.documento = datosActualizarPaciente.telefono();
        }
        if (datosActualizarPaciente.direccion()!=null){
            this.direccion=direccion.actualizarDatos(datosActualizarPaciente.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo=false;
    }
}
