package eu.jitpay.test.task.userlocations.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleException(EntityNotFoundException e) {
        log.error("Requested entity was not found", e);
        HttpStatus status = HttpStatus.NOT_FOUND;
        return getResponseEntity(status);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<String> handleException(ObjectOptimisticLockingFailureException e) {
        log.error("Optimistic locking failure", e);
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return getResponseEntity(status);
    }

    private static ResponseEntity<String> getResponseEntity(HttpStatus status) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.TEXT_PLAIN)
                .body(status.getReasonPhrase());
    }

}
