package kioke.commons.aspect;

import jakarta.servlet.http.HttpServletRequest;
import kioke.commons.annotation.HttpHeader;
import kioke.commons.annotation.HttpResponse;
import kioke.commons.http.HttpResponseBody;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class HttpResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(
      MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    if (!returnType.hasMethodAnnotation(HttpResponse.class)) {
      return false;
    }

    Class<?> clazz = returnType.getParameterType();
    return !ResponseEntity.class.isAssignableFrom(clazz);
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    HttpResponse responseAnnotation = returnType.getMethodAnnotation(HttpResponse.class);

    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest httpServletRequest = requestAttributes.getRequest();

    HttpStatus status = responseAnnotation.status();
    response.setStatusCode(status);

    HttpHeader[] headersAnnotation = responseAnnotation.headers();
    HttpHeaders responseHeaders = response.getHeaders();
    for (HttpHeader header : headersAnnotation) {
      responseHeaders.add(header.name(), header.value());
    }

    MediaType contentType = MediaType.valueOf(responseAnnotation.contentType());
    responseHeaders.setContentType(contentType);

    return HttpResponseBody.success(httpServletRequest, status, body);
  }
}
