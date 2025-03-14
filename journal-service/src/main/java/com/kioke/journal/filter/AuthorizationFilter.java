package com.kioke.journal.filter;

import com.kioke.journal.exception.security.TokenNotFoundException;
import com.kioke.journal.service.JwtService;
import com.kioke.journal.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

  private static final String[] WHITELIST = {"POST /users"};

  @Autowired UserService userService;
  @Autowired JwtService jwtService;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String method = request.getMethod();
    String path = request.getRequestURI();

    return Arrays.stream(WHITELIST)
        .anyMatch(
            item -> {
              var sub = item.split(" ");
              return method.equals(sub[0]) && path.equals(sub[1]);
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

    String uid = jwtService.extractSubject(token);
    if (uid != null) {
      var securityContext = SecurityContextHolder.getContext();

      if (securityContext.getAuthentication() == null) {
        UserDetails userDetails = userService.loadUserByUsername(uid);

        securityContext.setAuthentication(
            new UsernamePasswordAuthenticationToken(uid, null, userDetails.getAuthorities()));
      }
    }

    filterChain.doFilter(request, response);
  }
}
