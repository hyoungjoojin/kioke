package kioke.user.config;

import kioke.commons.filter.FilterExceptionHandler;
import kioke.commons.filter.RequestLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class FilterConfig {

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver exceptionResolver;

  @Bean
  public FilterRegistrationBean<FilterExceptionHandler> filterExceptionHandlerRegistrationBean() {
    FilterRegistrationBean<FilterExceptionHandler> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setFilter(new FilterExceptionHandler(exceptionResolver));
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilterRegistrationBean() {
    FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new RequestLoggingFilter());
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
    return registrationBean;
  }
}
