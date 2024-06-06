package dev.profitsoft.jfd.gatewaysample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception can be thrown when requested resource doesn't exist or now available
 * (deleted, disabled, not visible, etc.).
 * Will be converted automatically to 404 Not Found response code.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
  
}
