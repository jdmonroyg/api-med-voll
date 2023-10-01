package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;

/**
 * @author jdmon on 29/09/2023.
 * @project api-med-voll
 */
//tratar errores a nivel global no en método
@RestControllerAdvice
public class TratadorDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidation>> tratarError400(MethodArgumentNotValidException exception){
        var errores= exception.getFieldErrors().stream().map(DatosErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    private record DatosErrorValidation(String campo, String error){
        public DatosErrorValidation(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }

    }
}
