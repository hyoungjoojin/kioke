package kioke.notification;

import kioke.commons.configuration.SecurityConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(
    basePackages = {"kioke.notification"},
    basePackageClasses = {SecurityConfiguration.class})
@EnableDiscoveryClient
@EnableRabbit
@EnableJpaAuditing
public class KiokeNotificationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KiokeNotificationServiceApplication.class, args);
  }
}
