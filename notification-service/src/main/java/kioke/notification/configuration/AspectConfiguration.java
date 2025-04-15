package kioke.notification.configuration;

import kioke.commons.aspect.HttpResponseBodyAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfiguration {

  @Bean
  HttpResponseBodyAdvice httpResponseBodyAdvice() {
    return new HttpResponseBodyAdvice();
  }
}
