package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired //no se recomienda para testing
    private PacienteRepository pacienteRepository;
    @PostMapping
    @Operation(summary = "Registra un nuevo paciente")
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        //@Valid se utiliza para aplicar la validacion del DTO
        Paciente paciente=new Paciente(datosRegistroPaciente);
        pacienteRepository.save(paciente);
        DatosRespuestaPaciente  datosRespuestaPaciente= new DatosRespuestaPaciente(paciente);
        URI url=uriComponentsBuilder.path("pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado para los pacientes")
    public ResponseEntity<Page<DatosListadoPaciente>> listarPacientes (@PageableDefault(size = 5,sort = "nombre")Pageable paginacion){
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza las informaciones para el paciente")
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un paciente a partir del ID - Inactivo")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "obtiene los detalles para el paciente con el ID indicado")
    public ResponseEntity<DatosRespuestaPaciente> retornarDatosPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DatosRespuestaPaciente  datosRespuestaPaciente= new DatosRespuestaPaciente(paciente);
        return ResponseEntity.ok(datosRespuestaPaciente);
    }

}
