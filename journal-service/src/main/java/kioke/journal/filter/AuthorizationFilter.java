package kioke.journal.filter;

import java.util.List;
import kioke.commons.filter.AbstractAuthorizationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends AbstractAuthorizationFilter {

  @Override
  public List<HttpRequest> getWhitelist() {
    HttpRequest[] whitelist = {
      new HttpRequest(HttpMethod.POST, "/users"),
    };

    return List.of(whitelist);
  }
}
