package com.kioke.gateway.filter;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestLoggingFilter
    extends AbstractGatewayFilterFactory<RequestLoggingFilter.RequestLoggingFilterConfig> {

  public RequestLoggingFilter() {
    super(RequestLoggingFilterConfig.class);
  }

  @Override
  public GatewayFilter apply(RequestLoggingFilterConfig config) {
    return (exchange, chain) -> {
      var request = exchange.getRequest();

      String requestId = request.getId();
      HttpMethod requestMethod = request.getMethod();
      URI requestUri = request.getURI();

      log.info("[{}] HTTP {} {} recieved.", requestId, requestMethod, requestUri);

      exchange =
          exchange.mutate().request(r -> r.header("Kioke-Request-Id", requestId).build()).build();

      return chain
          .filter(exchange)
          .then(
              Mono.fromRunnable(
                  () -> {
                    log.info("[{}] HTTP {} {} finished.", requestId, requestMethod, requestUri);
                  }));
    };
  }

  public static class RequestLoggingFilterConfig {}
}
