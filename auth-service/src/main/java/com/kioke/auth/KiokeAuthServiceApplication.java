package com.kioke.auth;

import kioke.commons.filter.RequestLoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(
    basePackages = "com.kioke.auth",
    basePackageClasses = {RequestLoggingFilter.class})
public class KiokeAuthServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KiokeAuthServiceApplication.class, args);
  }
}
