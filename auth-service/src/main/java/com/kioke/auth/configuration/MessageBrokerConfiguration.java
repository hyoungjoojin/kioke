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

  @Value("${rabbitmq.queue.user-registration.name}")
  private String userRegistrationQueueName;

  @Value("${rabbitmq.routing.user-registration.name}")
  private String userRegistrationRoutingKeyName;

  @Bean
  public Queue userRegistrationQueue() {
    return new Queue(userRegistrationQueueName);
  }

  @Bean
  public FanoutExchange userRegistrationExchange() {
    return new FanoutExchange(userRegistrationExchangeName);
  }

  @Bean
  public Binding userRegistrationBinding() {
    return BindingBuilder.bind(userRegistrationQueue()).to(userRegistrationExchange());
  }

  @Bean
  public MessageConverter userRegistrationMessageConverter() {
    return new UserRegistrationMessageConverter();
  }
}
