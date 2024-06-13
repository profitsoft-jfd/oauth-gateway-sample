package dev.profitsoft.jfd.gatewaysample.gateway.filter;

import dev.profitsoft.jfd.gatewaysample.gateway.service.SessionService;
import dev.profitsoft.jfd.gatewaysample.gateway.service.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements GlobalFilter, Ordered {

  public static final String PREFIX_API = "/api";
  private final SessionService sessionService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (exchange.getRequest().getPath().value().startsWith(PREFIX_API)) {
      return sessionService.checkSession(exchange)
          .then(chain.filter(exchange))
          .onErrorResume(UnauthorizedException.class, e -> sendUnauthorized(exchange));
    }
    return chain.filter(exchange);
  }

  public static Mono<Void> sendUnauthorized(ServerWebExchange exchange) {
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
  }

  @Override
  public int getOrder() {
    return -5;
  }


}
