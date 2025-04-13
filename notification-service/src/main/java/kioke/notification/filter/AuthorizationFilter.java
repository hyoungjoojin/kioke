package kioke.notification.filter;

import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.commons.service.AbstractAuthService;
import kioke.notification.service.AuthService;
import kioke.notification.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends AbstractAuthorizationFilter {

  private final UserService userService;
  private final AuthService authService;

  public AuthorizationFilter(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
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
