package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
@Entity
@Table(name = "consultas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime date;

    @Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;

    public Consulta( Medico medico, Paciente paciente, LocalDateTime date) {
        this.medico=medico;
        this.paciente=paciente;
        this.date=date;
    }

    public Consulta(Medico medico, Paciente paciente, LocalDateTime date, MotivoCancelamiento motivo) {
        this.medico=medico;
        this.paciente=paciente;
        this.date=date;
        this.motivoCancelamiento=motivo;
    }

    public void cancelar(MotivoCancelamiento motivo) {
        this.motivoCancelamiento = motivo;
    }


}
