package kioke.journal.filter;

import java.util.List;
import kioke.commons.filter.AbstractAuthorizationFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends AbstractAuthorizationFilter {

  @Override
  public List<HttpRequest> getWhitelist() {
    HttpRequest[] whitelist = {};

    return List.of(whitelist);
  }
}
