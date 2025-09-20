package io.kioke.common.auth;

import io.kioke.feature.user.dto.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {

  private static final Logger logger =
      LoggerFactory.getLogger(AuthenticatedUserArgumentResolver.class);

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(AuthenticatedUser.class)
        && parameter.getParameterType().equals(UserPrincipal.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      logger.debug("Authentication is null");
      return null;
    }

    if (!authentication.isAuthenticated()) {
      logger.debug("Request is not authenticated");
      return null;
    }

    Object principal = authentication.getPrincipal();
    if (!(principal instanceof String)) {
      logger.warn("Unknown authentication principal type {}", principal.getClass());
      return null;
    }

    return UserPrincipal.of((String) principal);
  }
}
