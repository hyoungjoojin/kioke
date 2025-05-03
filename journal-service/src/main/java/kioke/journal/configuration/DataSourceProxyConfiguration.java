package kioke.journal.configuration;

import javax.sql.DataSource;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceProxyConfiguration {

  @Bean
  public DataSourceProxyBeanPostProcessor dataSourceProxyBeanPostProcessor() {
    return new DataSourceProxyBeanPostProcessor();
  }

  private static class DataSourceProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
      if (bean instanceof DataSource && !(bean instanceof ProxyDataSource)) {
        return ProxyDataSourceBuilder.create((DataSource) bean)
            .name("Journal Database DataSource")
            .logQueryBySlf4j(SLF4JLogLevel.DEBUG)
            .multiline()
            .countQuery()
            .build();
      }

      return bean;
    }
  }
}
