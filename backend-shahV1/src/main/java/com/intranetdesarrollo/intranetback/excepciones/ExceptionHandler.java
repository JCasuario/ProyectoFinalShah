package com.intranetdesarrollo.intranetback.excepciones;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value={DataIntegrityViolationException.class})
    public ResponseEntity<Object> uniqExceptionController(Exception ex, WebRequest request){

        String errorDescripcion = "Valores unicos duplicados";

        if(errorDescripcion == null) errorDescripcion = ex.toString();

        MensajeError mensajeError = new MensajeError(new Date(), errorDescripcion);

        return new ResponseEntity<>(
                mensajeError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value={ConstraintViolationException.class})
    public ResponseEntity<Object> constrainExceptionController(Exception ex, WebRequest request){

        String errorDescripcion = "Valores unicos duplicados";

        if(errorDescripcion == null) errorDescripcion = ex.toString();

        MensajeError mensajeError = new MensajeError(new Date(), errorDescripcion);

        return new ResponseEntity<>(
                mensajeError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "Usuario o contraseña erroneo");
            return errorDetail;
        } else if (exception instanceof AccountStatusException) {
            ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Acceso de cuenta bloqueado");
            return errorDetail;
        } else if (exception instanceof AccessDeniedException) {
            ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "No autorizado para el acceso");
            return errorDetail;
        } else if (exception instanceof SignatureException) {
            ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Sesión invalida");
            return errorDetail;
        } else if (exception instanceof ExpiredJwtException) {
            ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Sesión expirada");
            return errorDetail;
        } else {
            ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Error de servidor.");
            return errorDetail;
        }
    }
}
