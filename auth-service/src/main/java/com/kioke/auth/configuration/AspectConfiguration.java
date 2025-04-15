package com.kioke.auth.configuration;

import kioke.commons.aspect.HttpResponseBodyAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfiguration {

  @Bean
  public HttpResponseBodyAdvice httpResponseBodyAdvice() {
    return new HttpResponseBodyAdvice();
  }
}
