package kioke.user.config;

import kioke.commons.aspect.HttpResponseBodyAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

  @Bean
  public HttpResponseBodyAdvice httpResponseBodyAdvice() {
    return new HttpResponseBodyAdvice();
  }
}
