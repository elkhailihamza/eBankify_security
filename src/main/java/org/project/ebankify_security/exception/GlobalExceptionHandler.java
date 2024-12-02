package org.project.ebankify_security.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .message(ex.getMessage())
                .date(new Date())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ExceptionDetails> handleDataConflictException(EntityNotFoundException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .message(ex.getMessage())
                .date(new Date())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
    }
}
