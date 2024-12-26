package com.kioke.gateway;

import com.kioke.gateway.filter.RequestLoggingFilterFactory;
import com.kioke.gateway.filter.RequestLoggingFilterFactory.RequestLoggingFilterFactoryConfig;
import com.kioke.gateway.filter.TokenAuthenticationFilter;
import com.kioke.gateway.filter.TokenAuthenticationFilter.TokenAuthenticationFilterConfig;
import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
@EnableDiscoveryClient
public class KiokeApiGatewayApplication {

  @Bean
  public RouteLocator routeLocator(
      RouteLocatorBuilder routeLocatorBuilder,
      RequestLoggingFilterFactory requestLoggingFilterFactory,
      TokenAuthenticationFilter tokenAuthenticationFilter) {
    RequestLoggingFilterFactoryConfig requestLoggingFilterFactoryConfig =
        new RequestLoggingFilterFactoryConfig();

    TokenAuthenticationFilterConfig tokenAuthenticationFilterConfig =
        new TokenAuthenticationFilterConfig();

    Function<GatewayFilterSpec, GatewayFilterSpec> defaultFilter =
        f ->
            f.filter(requestLoggingFilterFactory.apply(requestLoggingFilterFactoryConfig))
                .removeRequestHeader("Kioke-Uid");

    Function<GatewayFilterSpec, GatewayFilterSpec> protectedRouteFilter =
        f -> f.filter(tokenAuthenticationFilter.apply(tokenAuthenticationFilterConfig));

    return routeLocatorBuilder
        .routes()
        .route(
            "Kioke Auth Service Login",
            route ->
                route
                    .method(HttpMethod.POST)
                    .and()
                    .path("/auth/login")
                    .filters(f -> defaultFilter.apply(f))
                    .uri("lb://auth"))
        .route(
            "Kioke Auth Service Registration",
            route ->
                route
                    .method(HttpMethod.POST)
                    .and()
                    .path("/auth/register")
                    .filters(f -> defaultFilter.apply(f))
                    .uri("lb://auth"))
        .route(
            "Kioke Get Authenticated User Inforrmation",
            route ->
                route
                    .method(HttpMethod.GET)
                    .and()
                    .path("/users")
                    .filters(f -> protectedRouteFilter.apply(defaultFilter.apply(f)))
                    .uri("lb://user"))
        .route(
            "Kioke Get User Inforrmation",
            route ->
                route
                    .method(HttpMethod.GET)
                    .and()
                    .path("/users/{uid}")
                    .filters(f -> defaultFilter.apply(f))
                    .uri("lb://user"))
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
        .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(KiokeApiGatewayApplication.class, args);
  }
}
