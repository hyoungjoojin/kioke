package com.kioke.auth.configuration;

import kioke.commons.dto.message.UserRegistrationMessageDto.UserRegistrationMessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfiguration {

  @Value("${rabbitmq.exchange.user-registration.name}")
  private String userRegistrationExchangeName;

  @Value("${rabbitmq.queue.user-registration.journal.name}")
  private String userRegistrationJournalQueueName;

  @Value("${rabbitmq.queue.user-registration.user.name}")
  private String userRegistrationUserQueueName;

  @Value("${rabbitmq.queue.user-registration.notification.name}")
  private String userRegistrationNotificationQueueName;

  @Bean
  public FanoutExchange userRegistrationExchange() {
    return new FanoutExchange(userRegistrationExchangeName);
  }

  @Bean
  public Queue userRegistrationJournalQueue() {
    return new Queue(userRegistrationJournalQueueName);
  }

  @Bean
  public Queue userRegistrationUserQueue() {
    return new Queue(userRegistrationUserQueueName);
  }

  @Bean
  public Queue userRegistrationNotificationQueue() {
    return new Queue(userRegistrationNotificationQueueName);
  }

  @Bean
  public Binding userRegistrationJournalBinding() {
    return BindingBuilder.bind(userRegistrationJournalQueue()).to(userRegistrationExchange());
  }

  @Bean
  public Binding userRegistrationUserBinding() {
    return BindingBuilder.bind(userRegistrationUserQueue()).to(userRegistrationExchange());
  }

  @Bean
  public Binding userRegistrationNotificationBinding() {
    return BindingBuilder.bind(userRegistrationNotificationQueue()).to(userRegistrationExchange());
  }

  @Bean
  public MessageConverter userRegistrationMessageConverter() {
    return new UserRegistrationMessageConverter();
  }
}
