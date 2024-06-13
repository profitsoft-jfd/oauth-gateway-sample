package dev.profitsoft.jfd.gatewaysample.gateway.service;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
