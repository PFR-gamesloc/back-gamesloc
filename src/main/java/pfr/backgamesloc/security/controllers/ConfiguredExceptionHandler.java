package pfr.backgamesloc.security.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ConfiguredExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(){
        return new ResponseEntity<>("Une erreur s'est produite. Veuillez réessayer plus tard.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e){
        return new ResponseEntity<>(e.getReason(),e.getStatusCode());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException e){
        return new ResponseEntity<>("Token Invalide",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
}
