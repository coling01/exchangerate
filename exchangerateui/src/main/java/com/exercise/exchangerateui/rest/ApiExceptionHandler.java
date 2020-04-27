package com.exercise.exchangerateui.rest;

import exceptions.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(value = AuthenticationException.class)
  protected ResponseEntity<Object> handleAuthentException(Exception e, WebRequest req) {
    log.error("Error caught in rest layer", e);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }

  @ExceptionHandler(value = Exception.class)
  protected ResponseEntity<Object> handleException(Exception e, WebRequest req) {
    log.error("Error caught in rest layer", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred retrieving Exchange Rates.");
  }

}
