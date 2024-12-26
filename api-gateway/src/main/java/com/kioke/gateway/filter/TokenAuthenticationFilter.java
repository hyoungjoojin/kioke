package com.kioke.gateway.filter;

import com.kioke.gateway.service.JwtService;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TokenAuthenticationFilter
    extends AbstractGatewayFilterFactory<
        TokenAuthenticationFilter.TokenAuthenticationFilterConfig> {
  @Autowired @Lazy private JwtService jwtService;

  public TokenAuthenticationFilter() {
    super(TokenAuthenticationFilterConfig.class);
  }

  @Override
  public GatewayFilter apply(TokenAuthenticationFilterConfig config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
      if (authHeaders == null || authHeaders.isEmpty()) {
        throw new ResponseStatusException(
            HttpStatus.FORBIDDEN, "Authorization headers could not be found in request headers.");
      }

      String authHeader = authHeaders.get(0);
      if (!authHeader.startsWith(TokenAuthenticationFilterConfig.AUTHORIZATION_HEADER_PREFIX)) {
        throw new ResponseStatusException(
            HttpStatus.FORBIDDEN,
            "Authorization header found in request headers does not follow the required format.");
      }

      String token =
          authHeader.substring(
              TokenAuthenticationFilterConfig.AUTHORIZATION_HEADER_PREFIX.length());

      if (!jwtService.isValidToken(token)) {
        throw new ResponseStatusException(
            HttpStatus.FORBIDDEN, "Authorization token found in request header is invalid.");
      }

      try {
        String uid = jwtService.extractSubject(token);
        exchange =
            exchange
                .mutate()
                .request(r -> r.headers(headers -> headers.set("Kioke-Uid", uid)))
                .build();

        return chain.filter(exchange);

      } catch (Exception e) {
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "User ID could not be found inside the authorization token.");
      }
    };
  }

  public static class TokenAuthenticationFilterConfig {
    public static String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
  }
}
