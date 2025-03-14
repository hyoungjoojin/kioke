package com.kioke.user.configuration;

import com.kioke.user.filter.AuthorizationFilter;
import com.kioke.user.filter.FilterExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired FilterExceptionHandler filterExceptionHandler;
  @Autowired AuthorizationFilter authorizationFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form.disable())
        .logout(logout -> logout.disable());

    httpSecurity
        .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterExceptionHandler, AuthorizationFilter.class);

    httpSecurity
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/user"))
                    .permitAll()
                    .anyRequest()
                    .authenticated());

    return httpSecurity.build();
  }
}
