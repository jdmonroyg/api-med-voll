package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jdmon on 28/09/2023.
 * @project api
 */
@RestController
@RequestMapping ("/medicos")
public class MedicoController {
    @Autowired //no se recomienda para testing
    private MedicoRepository medicoRepository;
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedicos) {
        medicoRepository.save(new Medico(datosRegistroMedicos));
    }

    @GetMapping
    public Page<DatosListadoMedico> listarMedicos(@PageableDefault(size = 5,sort = "nombre") Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
    }
    @PutMapping
    @Transactional // cuando termine el metodo la transaccion se va a liberar,
    //si la transaccion falla se hace rollback auto
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico= medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
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
    public void eliminarMedico(@PathVariable Long id){
        Medico medico= medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
    }

}
