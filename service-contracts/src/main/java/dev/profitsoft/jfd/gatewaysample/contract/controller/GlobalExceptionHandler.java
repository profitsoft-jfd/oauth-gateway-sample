package dev.profitsoft.jfd.gatewaysample.contract.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
    log.warn("IllegalArgumentException thrown: {}", e.getMessage());
    return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  private static ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
    ErrorResponse response = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message);
    return ResponseEntity.status(httpStatus.value()).body(response);
  }

  @Getter
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  static class ErrorResponse {
    private int status;
    private String error;
    private String message;
  }

}
