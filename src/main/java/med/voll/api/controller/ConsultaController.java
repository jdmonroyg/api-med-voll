package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author jdmon on 2/10/2023.
 * @project api-med-voll
 */
//@RestController // Se utiliza para servicios rest donde el formato es en json o xml
// Se utiliza para devolver una vista o nombre de vista que spring
//Resuelve para rendizar una page html
@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultaService service;
    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleConsulta> agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        service.agendar(datos);
        return ResponseEntity.ok(new DatosDetalleConsulta(datos.id(), datos.idPaciente(),
                datos.idMedico(), datos.date()));
    }
}
