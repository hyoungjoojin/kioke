package kioke.notification.configuration;

import kioke.commons.configuration.BaseSecurityConfiguration;
import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.notification.filter.AuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

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
}
