package com.kioke.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableDiscoveryClient
public class KiokeAuthServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KiokeAuthServiceApplication.class, args);
  }
}
