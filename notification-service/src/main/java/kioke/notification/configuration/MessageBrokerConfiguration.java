package kioke.notification.configuration;

import kioke.commons.dto.message.UserRegistrationMessageDto.UserRegistrationMessageConverter;
import kioke.commons.dto.message.notification.NotificationMessageDto.NotificationMessageConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MessageBrokerConfiguration {

  @Value("${rabbitmq.queue.user-registration.name}")
  private String userRegistrationQueueName;

  @Value("${rabbitmq.queue.notification.name}")
  private String notificationQueueName;

  @Bean
  public Queue userRegistrationQueue() {
    return new Queue(userRegistrationQueueName);
  }

  @Bean
  public Queue notificationQueue() {
    return new Queue(notificationQueueName);
  }

  @Bean
  public MessageConverter userRegistrationMessageConverter() {
    return new UserRegistrationMessageConverter();
  }

  @Bean
  public MessageConverter notificationMessageConverter() {
    return new NotificationMessageConverter();
  }
}
