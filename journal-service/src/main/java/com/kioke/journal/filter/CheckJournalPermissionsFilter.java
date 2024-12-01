package com.kioke.journal.filter;

import com.kioke.journal.constant.KiokeServices;
import com.kioke.journal.dto.external.auth.AuthServiceGetJournalPermissionsRequestBodyDto;
import com.kioke.journal.dto.external.auth.AuthServiceGetJournalPermissionsResponseBodyDto;
import com.kioke.journal.service.DiscoveryClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(4)
@Slf4j
public class CheckJournalPermissionsFilter extends OncePerRequestFilter {
  @Autowired @Lazy DiscoveryClientService discoveryClientService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String uid = (String) request.getAttribute("uid");
    String jid = getJournalIdFromRequestPath(request.getRequestURI());
    if (jid == "") {
      filterChain.doFilter(request, response);
    }

    try {
      AuthServiceGetJournalPermissionsResponseBodyDto authServiceResponse =
          discoveryClientService
              .getRestClient(KiokeServices.AUTH_SERVICE, "")
              .method(HttpMethod.GET)
              .body(
                  AuthServiceGetJournalPermissionsRequestBodyDto.builder()
                      .uid(uid)
                      .jid(jid)
                      .build())
              .retrieve()
              .body(
                  new ParameterizedTypeReference<
                      AuthServiceGetJournalPermissionsResponseBodyDto>() {});

      log.info(authServiceResponse.getPermissions().toString());
    } catch (Exception e) {
      log.error(e.toString());
      return;
    }
  }

  private String getJournalIdFromRequestPath(String requestPath) {
    String[] requestPathParts = requestPath.split("/");
    return requestPathParts.length == 2 ? "" : requestPathParts[2];
  }
}
