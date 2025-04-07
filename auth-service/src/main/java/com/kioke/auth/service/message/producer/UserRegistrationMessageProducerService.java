package com.kioke.auth.service.message.producer;

import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.service.message.AbstractMessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRegistrationMessageProducerService
    extends AbstractMessageProducerService<UserRegistrationMessageDto> {

  @Autowired private RabbitTemplate rabbitTemplate;
  @Autowired private Exchange userRegistrationExchange;
  @Autowired private MessageConverter userRegistrationMessageConverter;

  @Override
  public void send(UserRegistrationMessageDto object) {
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

    rabbitTemplate.convertAndSend(
        userRegistrationExchange.getName(),
        "",
        userRegistrationMessageConverter.toMessage(object, messageProperties));
  }
}
