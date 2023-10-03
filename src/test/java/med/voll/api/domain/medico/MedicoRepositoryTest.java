package med.voll.api.domain.medico;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * @author jdmon on 3/10/2023.
 * @project api-med-voll
 */
@DataJpaTest //para poder testear las capas de persistencia (Repository)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;
    @Test
    @DisplayName("Deberia retonar nulo cuando el medico se encuentre en consulta" +
            " con otro paciente en ese horario ")
    @Transactional
    void seleccionarMedicoConEspecilidadEnFechaEscenario1() {
        //given
        var proximoLunes10H= LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(12,0);

        var paciente= registrarPaciente();
        var medico= registrarMedico();
        registrarConsulta(medico,paciente,proximoLunes10H);

        //when
        var medicoLibre=medicoRepository.SeleccionarMedicoConEspecilidadEnFecha(
                Especialidad.CARDIOLOGIA,proximoLunes10H
        );

        //then
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia retonar un medico cuando realice la consulta en la base de datos" +
            " para ese horario ")
    void seleccionarMedicoConEspecilidadEnFechaEscenario2() {
        //given
        var proximoLunes10H= LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(12,0);
        var medico= registrarMedico();

        //when
        var medicoLibre=medicoRepository.SeleccionarMedicoConEspecilidadEnFecha(
                Especialidad.CARDIOLOGIA,proximoLunes10H
        );

        //then
        assertThat(medicoLibre).isEqualTo(medico);
    }
    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta( medico, paciente, fecha,null));
    }



    private Medico registrarMedico() {
        var medico = new Medico(datosMedico(
        ));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente() {
        var paciente = new Paciente(datosPaciente());
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico() {
        return new DatosRegistroMedico(
                "Jose",
                "jose@email.com",
                "61999999999",
                "778811",
                Especialidad.CARDIOLOGIA,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente() {
        return new DatosRegistroPaciente(
                "antonio",
                "antonio@email.com",
                "229957",
                "61999999999",
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "loca",
                "azul",
                "acapulpo",
                "321",
                "B"
        );
    }
}