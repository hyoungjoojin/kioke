package com.kioke.gateway.filter;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestLoggingFilterFactory
    extends AbstractGatewayFilterFactory<
        RequestLoggingFilterFactory.RequestLoggingFilterFactoryConfig> {

  @Override
  public GatewayFilter apply(RequestLoggingFilterFactoryConfig config) {
    return (exchange, chain) -> {
      HttpMethod requestMethod = exchange.getRequest().getMethod();
      URI requestUri = exchange.getRequest().getURI();

      log.info("HTTP {} {} recieved.", requestMethod, requestUri);

      log.info(exchange.getResponse().toString());
      return chain.filter(exchange);
    };
  }

  public static class RequestLoggingFilterFactoryConfig {}
}
