package com.taklip.yoda.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 *
 * User for rest api
 */
@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("GlobalRestExceptionHandler", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // @ExceptionHandler(UserNotFoundException.class)
    // public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
    //     log.info("User not found: {}", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //         .body(new ErrorResponse("USER_NOT_FOUND", "User not found"));
    // }
    
    // @ExceptionHandler(ValidationException.class)
    // public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
    //     log.warn("Validation failed: {}", ex.getMessage());
    //     return ResponseEntity.badRequest()
    //         .body(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
    // }
    
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<String> handleGeneral(Exception ex) {
    //     log.error("Unexpected error", ex);
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body("Something went wrong");
    // }
}