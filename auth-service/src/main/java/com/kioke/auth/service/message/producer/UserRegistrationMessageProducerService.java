package com.kioke.auth.service.message.producer;

import com.kioke.auth.configuration.MessageBrokerConfiguration;
import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.service.message.AbstractMessageProducerService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationMessageProducerService
    extends AbstractMessageProducerService<UserRegistrationMessageDto> {

  @Autowired private MessageBrokerConfiguration messageBrokerConfiguration;

  @Override
  protected Exchange getExchange() {
    return messageBrokerConfiguration.userRegistrationExchange();
  }

  @Override
  protected Queue getQueue() {
    return messageBrokerConfiguration.userRegistrationQueue();
  }

  @Override
  protected Binding getBinding() {
    return messageBrokerConfiguration.userRegistrationBinding();
  }

  @Override
  protected String getRoutingKey() {
    return null;
  }

  @Override
  protected MessageConverter getMessageConverter() {
    return messageBrokerConfiguration.userRegistrationMessageConverter();
  }
}
