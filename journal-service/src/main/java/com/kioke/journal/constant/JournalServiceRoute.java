package com.kioke.journal.constant;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpMethod;

public enum JournalServiceRoute {
  CREATE_JOURNAL(HttpMethod.POST, "/journals", new Permission[] {}),
  GET_JOURNAL(HttpMethod.GET, "/journals/{jid}", new Permission[] {});

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

      String[] pathParts = route.getPath().split("/");
      if (pathParts.length != requestPathParts.length) continue;

      boolean matches = true;
      for (int i = 0; i < pathParts.length; i++) {
        if (pathParts[i].indexOf("{") == -1 && pathParts[i] != requestPathParts[i]) {
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
      if (pathParts[i] == "{jid}") {
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
