package kioke.journal;

import kioke.commons.configuration.SecurityConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(
    basePackages = {"kioke.journal"},
    basePackageClasses = {SecurityConfiguration.class})
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableRabbit
public class KiokeJournalServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KiokeJournalServiceApplication.class, args);
  }
}
