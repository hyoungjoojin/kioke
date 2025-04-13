package kioke.commons.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import kioke.commons.exception.security.TokenNotFoundException;
import kioke.commons.service.AbstractAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AbstractAuthorizationFilter extends OncePerRequestFilter {

  public List<HttpRequest> getWhitelist() {
    HttpRequest[] whitelist = {};
    return List.of(whitelist);
  }

  protected abstract UserDetailsService getUserDetailsService();

  protected abstract AbstractAuthService getAuthService();

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return getWhitelist().stream()
        .anyMatch(
            whitelistItem -> {
              return whitelistItem.getUri().equals(request.getRequestURI())
                  && whitelistItem.getMethod().toString().equals(request.getMethod());
            });
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
      throw new TokenNotFoundException();
    }

    final String token = authorizationHeader.substring(7);

    String uid = getAuthService().extractSubjectFromToken(token);
    if (uid != null) {
      var securityContext = SecurityContextHolder.getContext();

      if (securityContext.getAuthentication() == null) {
        UserDetails userDetails = getUserDetailsService().loadUserByUsername(uid);

        securityContext.setAuthentication(
            new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()));
      }
    }

    filterChain.doFilter(request, response);
  }

  public static class HttpRequest {
    private HttpMethod method;
    private String uri;

    public HttpRequest(HttpMethod method, String uri) {
      this.method = method;
      this.uri = uri;
    }

    public HttpMethod getMethod() {
      return method;
    }

    public String getUri() {
      return uri;
    }
  }
}
