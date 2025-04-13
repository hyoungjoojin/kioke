package kioke.user.filter;

import kioke.commons.filter.AbstractAuthorizationFilter;
import kioke.commons.service.AbstractAuthService;
import kioke.user.service.AuthService;
import kioke.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends AbstractAuthorizationFilter {

  private final AuthService authService;
  private final UserService userService;

  public AuthorizationFilter(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
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
