package com.kioke.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class KiokeApiGatewayApplication {

  @Bean
  public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder
        .routes()
        .route(route -> route.path("/users/**").uri("lb://user"))
        .route(route -> route.path("/auth/**").uri("lb://auth"))
        .route(route -> route.path("/journals/**").uri("lb://journal"))
        .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(KiokeApiGatewayApplication.class, args);
  }
}
