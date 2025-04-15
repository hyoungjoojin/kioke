package kioke.commons.configuration;

import kioke.commons.filter.AbstractAuthorizationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public abstract class BaseSecurityConfiguration {

  protected abstract AbstractAuthorizationFilter getAuthorizationFilter();

  protected SecurityFilterChain buildSecurityFilterChain(HttpSecurity httpSecurity)
      throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form.disable())
        .logout(logout -> logout.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> {
              getAuthorizationFilter()
                  .getWhitelist()
                  .forEach(
                      (whitelistItem) -> {
                        authorize
                            .requestMatchers(
                                AntPathRequestMatcher.antMatcher(
                                    whitelistItem.getMethod(), whitelistItem.getUri()))
                            .permitAll();
                      });
              authorize.anyRequest().authenticated();
            })
        .addFilterBefore(getAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
