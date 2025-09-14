package io.kioke.config;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.QueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JSlowQueryListener;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

  private final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username:''}")
  private String username;

  @Value("${spring.datasource.password:''}")
  private String password;

  @Value("${spring.datasource.driverClassName:''}")
  private String driverClassName;

  @Bean
  public DataSource datasource() {
    DataSource dataSource =
        DataSourceBuilder.create()
            .url(url)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build();

    SLF4JSlowQueryListener slowQueryListener =
        new SLF4JSlowQueryListener(100, TimeUnit.MILLISECONDS);
    slowQueryListener.setQueryLogEntryCreator(logEntryCreator);
    slowQueryListener.setLogLevel(SLF4JLogLevel.DEBUG);

    SLF4JQueryLoggingListener queryListener = new SLF4JQueryLoggingListener();
    queryListener.setQueryLogEntryCreator(logEntryCreator);
    queryListener.setLogLevel(SLF4JLogLevel.DEBUG);

    return ProxyDataSourceBuilder.create(dataSource)
        .name("auth-service-datasource")
        .listener(queryListener)
        .listener(slowQueryListener)
        .afterQuery(
            (execInfo, _) -> {
              logger.debug("Elapsed time: " + execInfo.getElapsedTime() + "ms");
            })
        .multiline()
        .countQuery()
        .build();
  }

  private static final QueryLogEntryCreator logEntryCreator =
      new DefaultQueryLogEntryCreator() {

        @Override
        protected void writeParamsEntry(
            StringBuilder sb, ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
          sb.append("Params:[");

          for (QueryInfo queryInfo : queryInfoList) {
            for (List<ParameterSetOperation> parameters : queryInfo.getParametersList()) {
              sb.append("(");

              for (ParameterSetOperation operation : parameters) {
                Object parameter = operation.getArgs()[1];
                sb.append((parameter == null ? "null" : parameter) + ",");
              }

              chompIfEndWith(sb, ',');
              sb.append("),");
            }

            chompIfEndWith(sb, ',');
          }
          chompIfEndWith(sb, ',');

          sb.append("]");
        }
      };
}
