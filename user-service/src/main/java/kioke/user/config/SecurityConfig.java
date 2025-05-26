package kioke.user.config;

import kioke.commons.configuration.BaseSecurityConfiguration;
import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.user.common.auth.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends BaseSecurityConfiguration {

  private final AuthFilter authFilter;

  public SecurityConfig(AuthFilter authFilter) {
    this.authFilter = authFilter;
  }

  @Override
  protected AbstractAuthorizationFilter getAuthorizationFilter() {
    return authFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return super.buildSecurityFilterChain(httpSecurity);
  }
}
