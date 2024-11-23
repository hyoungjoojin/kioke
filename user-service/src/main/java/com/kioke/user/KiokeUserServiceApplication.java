package com.kioke.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KiokeUserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KiokeUserServiceApplication.class, args);
  }
}
