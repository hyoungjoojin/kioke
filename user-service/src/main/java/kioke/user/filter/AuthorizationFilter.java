package kioke.user.filter;

import java.util.List;
import kioke.commons.filter.AbstractAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationFilter extends AbstractAuthorizationFilter {

  @Override
  public List<HttpRequest> getWhitelist() {
    HttpRequest[] whitelist = {new HttpRequest(HttpMethod.POST, "/users")};

    return List.of(whitelist);
  }
}
