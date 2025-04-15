package kioke.user.configuration;

import kioke.commons.configuration.BaseSecurityConfiguration;
import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.user.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends BaseSecurityConfiguration {

  private final AuthorizationFilter authorizationFilter;

  public SecurityConfiguration(AuthorizationFilter authorizationFilter) {
    this.authorizationFilter = authorizationFilter;
  }

  @Override
  protected AbstractAuthorizationFilter getAuthorizationFilter() {
    return authorizationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return super.buildSecurityFilterChain(httpSecurity);
  }
}
