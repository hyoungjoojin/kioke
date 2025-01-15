package com.kioke.journal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class ExtractHeaderValuesToAttributesFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestId = request.getHeader("Kioke-Request-Id");
    if (!Objects.isNull(requestId)) {
      request.setAttribute("requestId", requestId);
    }

    String uid = request.getHeader("Kioke-Uid");
    if (!Objects.isNull(uid)) {
      request.setAttribute("uid", uid);
    }

    doFilter(request, response, filterChain);
  }
}
