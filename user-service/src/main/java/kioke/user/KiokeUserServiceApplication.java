package kioke.user;

import kioke.commons.configuration.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
    basePackages = {"kioke.user"},
    basePackageClasses = {SecurityConfiguration.class})
@EnableDiscoveryClient
public class KiokeUserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KiokeUserServiceApplication.class, args);
  }
}
