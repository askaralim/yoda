package com.taklip.yoda.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author askar
 *
 * User for not rest api
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_ERROR_VIEW = "500";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception e, HttpServletRequest request) {
        logger.info("Request URL: " + request.getRequestURL());
        logger.error("Error: ", e);

        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }
}