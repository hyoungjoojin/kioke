package com.kioke.journal.filter;

import com.kioke.journal.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@Order(2)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

  @Autowired @Lazy private JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null
        || !authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = authorizationHeader.substring(AUTHORIZATION_HEADER_PREFIX.length());
    final String uid = jwtService.extractSubject(token);

    if (uid != null && jwtService.isValidToken(token)) {
      request.setAttribute("uid", uid);
    }

    filterChain.doFilter(request, response);
  }
}
