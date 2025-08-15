package io.kioke.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable());

    httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

    httpSecurity
        .formLogin(formLogin -> formLogin.disable())
        .logout(logout -> logout.disable())
        .rememberMe(rememberMe -> rememberMe.disable());

    httpSecurity.authorizeHttpRequests(
        requests ->
            requests
                .requestMatchers(HttpMethod.POST, "/auth/signup")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/signin")
                .permitAll()
                .anyRequest()
                .authenticated());

    httpSecurity.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

    return httpSecurity.build();
  }

  @Bean
  protected CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration
        .setAllowedOriginPatterns(Arrays.asList("*"))
        .setAllowedMethods(Arrays.asList("*"));

    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider =
        new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    int saltLength = 16, hashLength = 32, parallelism = 1, memory = 1 << 16, numIterations = 2;
    return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, numIterations);
  }
}
