package com.kioke.journal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AssignRequestAttributesFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestId = UUID.randomUUID().toString();
    request.setAttribute("requestId", requestId);

    String path = request.getRequestURI();
    request.setAttribute("path", path);

    OffsetDateTime timestamp = OffsetDateTime.now();
    request.setAttribute("timestamp", timestamp);

    filterChain.doFilter(request, response);
  }
}
