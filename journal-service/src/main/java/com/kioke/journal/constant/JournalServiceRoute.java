package com.kioke.journal.constant;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

@Slf4j
public enum JournalServiceRoute {
  CREATE_JOURNAL(HttpMethod.POST, "/journals", new Permission[] {Permission.CREATE}),
  GET_JOURNAL(HttpMethod.GET, "/journals/{jid}", new Permission[] {Permission.READ});

  HttpMethod httpMethod;
  String path;
  Permission[] requiredPermissions;

  private JournalServiceRoute(HttpMethod httpMethod, String path, Permission[] permissions) {
    this.httpMethod = httpMethod;
    this.path = path;
    this.requiredPermissions = permissions;
  }

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public String getPath() {
    return path;
  }

  public static Optional<JournalServiceRoute> getJournalServiceRouteFromRequest(
      HttpServletRequest request) {
    HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
    String[] requestPathParts = request.getRequestURI().split("/");

    for (JournalServiceRoute route : JournalServiceRoute.values()) {
      if (!route.getHttpMethod().equals(requestMethod)) continue;

      String[] routePathParts = route.getPath().split("/");
      if (routePathParts.length != requestPathParts.length) continue;

      boolean matches = true;
      for (int i = 0; i < routePathParts.length; i++) {
        if (routePathParts[i].indexOf("{") == -1
            && !routePathParts[i].equals(requestPathParts[i])) {
          matches = false;
          break;
        }
      }

      if (matches) {
        return Optional.of(route);
      }
    }

    return Optional.empty();
  }

  public String extractJournalId(String requestUri) {
    String[] pathParts = path.split("/");
    String[] requestPathParts = requestUri.split("/");

    for (int i = 0; i < pathParts.length; i++) {
      if (pathParts[i].equals("{jid}")) {
        return requestPathParts[i];
      }
    }

    return "";
  }

  public boolean isPermitted(List<Permission> permissions) {
    for (Permission requiredPermission : requiredPermissions) {
      if (permissions.indexOf(requiredPermission) == -1) return false;
    }

    return true;
  }
}
