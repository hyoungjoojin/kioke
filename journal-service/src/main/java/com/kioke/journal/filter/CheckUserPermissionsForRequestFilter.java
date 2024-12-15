package com.kioke.journal.filter;

import com.kioke.journal.constant.JournalServiceRoute;
import com.kioke.journal.constant.KiokeServices;
import com.kioke.journal.constant.Permission;
import com.kioke.journal.dto.external.auth.GetPermissionsResponseBodyDto;
import com.kioke.journal.service.DiscoveryClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(4)
@Slf4j
public class CheckUserPermissionsForRequestFilter extends OncePerRequestFilter {
  @Autowired @Lazy private DiscoveryClientService discoveryClientService;

  private RestClient restClient = RestClient.create();

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String uid = request.getHeader("Kioke-Uid");
    if (uid == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized.");
    }

    request.setAttribute("uid", uid);

    JournalServiceRoute journalServiceRoute =
        JournalServiceRoute.getJournalServiceRouteFromRequest(request)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Given route is not accessible."));

    String jid = journalServiceRoute.extractJournalId(request.getRequestURI());

    try {
      String authServiceUri = discoveryClientService.getServiceUri(KiokeServices.AUTH_SERVICE);

      List<Permission> permissions =
          restClient
              .get()
              .uri(authServiceUri + "/permissions/" + uid + (jid.isBlank() ? "" : ("?jid=" + jid)))
              .retrieve()
              .body(new ParameterizedTypeReference<GetPermissionsResponseBodyDto>() {})
              .intoPermissionsList();

      if (journalServiceRoute.isPermitted(permissions)) {
        filterChain.doFilter(request, response);
      }

    } catch (Exception e) {
      log.error(e.toString());
      return;
    }
  }
}
