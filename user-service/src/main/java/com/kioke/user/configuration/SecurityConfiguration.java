package com.kioke.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  private static final Integer ARGON2_PASSWORD_ENCODER_SALT_LENGTH = 16;
  private static final Integer ARGON2_PASSWORD_ENCODER_HASH_LENGTH = 32;
  private static final Integer ARGON2_PASSWORD_ENCODER_PARALLELISM = 1;
  private static final Integer ARGON2_PASSWORD_ENCODER_MEMORY = 1 << 14;
  private static final Integer ARGON2_PASSWORD_ENCODER_NUM_ITERATIONS = 2;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Argon2PasswordEncoder(
        ARGON2_PASSWORD_ENCODER_SALT_LENGTH,
        ARGON2_PASSWORD_ENCODER_HASH_LENGTH,
        ARGON2_PASSWORD_ENCODER_PARALLELISM,
        ARGON2_PASSWORD_ENCODER_MEMORY,
        ARGON2_PASSWORD_ENCODER_NUM_ITERATIONS);
  }
}
