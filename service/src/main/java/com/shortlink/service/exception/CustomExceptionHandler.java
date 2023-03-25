package com.shortlink.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //allows handling exceptions across the whole application in one global handling component
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(URLInvalidException.class)
    public ResponseEntity<CustomErrorResponse> handleURLInvalidException(URLInvalidException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}