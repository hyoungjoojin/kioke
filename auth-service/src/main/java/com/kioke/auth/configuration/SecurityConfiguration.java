package com.kioke.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
  private static final Integer ARGON2_PASSWORD_ENCODER_SALT_LENGTH = 16;
  private static final Integer ARGON2_PASSWORD_ENCODER_HASH_LENGTH = 32;
  private static final Integer ARGON2_PASSWORD_ENCODER_PARALLELISM = 1;
  private static final Integer ARGON2_PASSWORD_ENCODER_MEMORY = 1 << 14;
  private static final Integer ARGON2_PASSWORD_ENCODER_NUM_ITERATIONS = 2;

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
        .formLogin(formLogin -> formLogin.disable())
        .authorizeExchange(
            exchange ->
                exchange
                    .pathMatchers("/auth/login", "/auth/register")
                    .permitAll()
                    .anyExchange()
                    .authenticated())
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }

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
