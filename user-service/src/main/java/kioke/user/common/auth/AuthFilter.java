package kioke.user.common.auth;

import java.util.List;
import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.commons.service.AbstractAuthService;
import kioke.user.feature.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthFilter extends AbstractAuthorizationFilter {

  private final AuthService authService;
  private final UserService userService;

  public AuthFilter(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @Override
  public List<HttpRequest> getWhitelist() {
    HttpRequest[] whitelist = {
      new HttpRequest(HttpMethod.GET, "/user-service/api-docs"),
      new HttpRequest(HttpMethod.GET, "/user-service/swagger-ui.html")
    };

    return List.of(whitelist);
  }

  @Override
  protected AbstractAuthService getAuthService() {
    return authService;
  }

  @Override
  protected UserDetailsService getUserDetailsService() {
    return userService;
  }
}
