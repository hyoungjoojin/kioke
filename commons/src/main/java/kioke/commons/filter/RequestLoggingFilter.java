package kioke.commons.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String correlationId = request.getHeader("X-Correlation-ID");
    if (correlationId == null) {
      correlationId = UUID.randomUUID().toString();
    }

    request.setAttribute("requestId", correlationId);
    filterChain.doFilter(request, response);
  }
}
