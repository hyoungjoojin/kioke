package io.kioke.feature.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

  public HttpSession createSession(HttpServletRequest request, Authentication authentication) {
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);

    HttpSession session = request.getSession(true);
    session.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

    return session;
  }

  public void destroySession(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();
    }
  }
}
