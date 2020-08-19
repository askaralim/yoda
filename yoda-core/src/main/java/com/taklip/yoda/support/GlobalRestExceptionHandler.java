package com.taklip.yoda.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author askar
 *
 * User for rest api
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}