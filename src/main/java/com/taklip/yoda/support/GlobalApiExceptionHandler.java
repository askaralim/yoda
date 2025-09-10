package com.taklip.yoda.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.taklip.yoda.api.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalApiExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationExceptions(
                        MethodArgumentNotValidException ex, WebRequest request) {

                List<String> details = ex.getBindingResult().getFieldErrors().stream()
                                .map(FieldError::getDefaultMessage).collect(Collectors.toList());

                ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR",
                                "Validation failed", request.getDescription(false));
                errorResponse.setDetails(details);

                log.warn("Validation error: {}", details);
                return ResponseEntity.badRequest().body(errorResponse);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorResponse> handleConstraintViolation(
                        ConstraintViolationException ex, WebRequest request) {

                List<String> details = ex.getConstraintViolations().stream()
                                .map(violation -> violation.getPropertyPath() + ": "
                                                + violation.getMessage())
                                .collect(Collectors.toList());

                ErrorResponse errorResponse = new ErrorResponse("CONSTRAINT_VIOLATION",
                                "Constraint violation", request.getDescription(false));
                errorResponse.setDetails(details);

                log.warn("Constraint violation: {}", details);
                return ResponseEntity.badRequest().body(errorResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception ex,
                        WebRequest request) {

                ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR",
                                "An unexpected error occurred", request.getDescription(false));

                log.error("Unexpected error: ", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
                        IllegalArgumentException ex, WebRequest request) {

                ErrorResponse errorResponse = new ErrorResponse("INVALID_ARGUMENT", ex.getMessage(),
                                request.getDescription(false));

                log.warn("Invalid argument: {}", ex.getMessage());
                return ResponseEntity.badRequest().body(errorResponse);
        }
}
