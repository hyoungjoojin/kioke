package kioke.journal.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.commons.service.AbstractAuthService;
import kioke.journal.service.AuthService;
import kioke.journal.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends AbstractAuthorizationFilter {

  private final UserService userService;
  private final AuthService authService;

  private final List<HttpRequest> whitelist =
      Collections.unmodifiableList(
          Arrays.asList(
              new HttpRequest(HttpMethod.GET, "/v3/api-docs/journal"),
              new HttpRequest(HttpMethod.GET, "/actuator"),
              new HttpRequest(HttpMethod.GET, "/actuator/health"),
              new HttpRequest(HttpMethod.GET, "/actuator/info"),
              new HttpRequest(HttpMethod.GET, "/actuator/prometheus"),
              new HttpRequest(HttpMethod.GET, "/actuator/metrics")));

  public AuthorizationFilter(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @Override
  public List<HttpRequest> getWhitelist() {
    return whitelist;
  }

  @Override
  protected UserDetailsService getUserDetailsService() {
    return userService;
  }

  @Override
  protected AbstractAuthService getAuthService() {
    return authService;
  }
}
