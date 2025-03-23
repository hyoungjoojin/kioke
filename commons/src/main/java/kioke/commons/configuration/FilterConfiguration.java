package kioke.commons.configuration;

import kioke.commons.filter.FilterExceptionHandler;
import kioke.commons.filter.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfiguration {

  @Bean
  public FilterRegistrationBean<FilterExceptionHandler> filterExceptionHandlerRegistrationBean() {
    FilterRegistrationBean<FilterExceptionHandler> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setFilter(new FilterExceptionHandler());
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
