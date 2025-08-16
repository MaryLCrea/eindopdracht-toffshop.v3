package toff.novi.eindopdrachttoffshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> ResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e -> errorResponse.put(e.getField(), e.getDefaultMessage()));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}