package med.voll.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
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
@RequestMapping ("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired //no se recomienda para testing
    private MedicoRepository medicoRepository;
    @PostMapping
    @Operation(summary = "Registra un nuevo medico en la base de datos")
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedicos,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Medico medico=medicoRepository.save(new Medico(datosRegistroMedicos));
        //return 201 created
        //url donde encontrar el med
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        URI url= uriComponentsBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de medicos")
    public ResponseEntity<Page<DatosListadoMedico>> listarMedicos(@PageableDefault(size = 5,sort = "nombre") Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional // cuando termine el metodo la transaccion se va a liberar,
    //si la transaccion falla se hace rollback auto
    @Operation(summary = "Actualiza los datos de un medico existente")
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico= medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico));
    }
//  delet base de datos
//    @DeleteMapping("/{id}") //@pathvariable enlaza el parametro {id}
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico= medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    //@Secured("ROLE_ADMIN") se utiliza para que los admin puedan ejecutar el metodo
    //ResponseEntity se usa para cambiar el codigo de respuesta
    @Operation(summary = "Elimina un medico registrado - inactivo")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id){
        Medico medico= medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    //ResponseEntity se usa para cambiar el codigo de respuesta
    @Operation(summary = "Obtiene los registros del medico con ID")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id){
        Medico medico= medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico);
        return ResponseEntity.ok(datosMedico);
    }

}
