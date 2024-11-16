package com.kioke.journal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestId = UUID.randomUUID().toString();
    request.setAttribute("requestId", requestId);

    String path = request.getRequestURI();
    request.setAttribute("path", path);

    OffsetDateTime start = OffsetDateTime.now();
    request.setAttribute("timestamp", start);

    log.info(
        "(requestId={}) HTTP {} {} recieved at {}.", requestId, request.getMethod(), path, start);

    filterChain.doFilter(request, response);

    OffsetDateTime end = OffsetDateTime.now();
    log.info(
        "(requestId={}) HTTP response took {} ms.",
        requestId,
        Duration.between(start, end).toMillisPart());
  }
}
