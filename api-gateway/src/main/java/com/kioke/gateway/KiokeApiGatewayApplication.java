package com.kioke.gateway;

import com.kioke.gateway.filter.TokenAuthenticationFilter;
import com.kioke.gateway.filter.TokenAuthenticationFilter.TokenAuthenticationFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
@EnableDiscoveryClient
public class KiokeApiGatewayApplication {

  @Bean
  public RouteLocator routeLocator(
      RouteLocatorBuilder routeLocatorBuilder,
      TokenAuthenticationFilter tokenAuthenticationFilter) {
    TokenAuthenticationFilterConfig tokenAuthenticationFilterConfig =
        new TokenAuthenticationFilterConfig();

    return routeLocatorBuilder
        .routes()
        .route(
            "Kioke Auth Service Login",
            route -> route.method(HttpMethod.POST).and().path("/auth/login").uri("lb://auth"))
        .route(
            "Kioke Auth Service Registration",
            route -> route.method(HttpMethod.POST).and().path("/auth/register").uri("lb://auth"))
        .route(
            "Kioke Journal Service",
            route ->
                route
                    .path("/journals/**")
                    .filters(
                        f ->
                            f.filter(
                                tokenAuthenticationFilter.apply(tokenAuthenticationFilterConfig)))
                    .uri("lb://journal"))
        .route(
            "user-service",
            route ->
                route
                    .path("/users/**")
                    .and()
                    .not(r -> r.method("POST").and().path("/users"))
                    .filters(
                        f ->
                            f.filter(
                                tokenAuthenticationFilter.apply(
                                    new TokenAuthenticationFilterConfig())))
                    .uri("lb://user"))
        .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(KiokeApiGatewayApplication.class, args);
  }
}
