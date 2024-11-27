package com.kioke.journal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kioke.journal.dto.message.CreateJournalMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducerService {

  @Autowired @Lazy private RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.queue.name}")
  private String queueName;

  @Value("${rabbitmq.exchange.name}")
  private String exchangeName;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  public void createJournal(CreateJournalMessageDto message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(message);
      rabbitTemplate.convertAndSend(exchangeName, routingKey, json);
    } catch (JsonProcessingException e) {
      log.error(e.toString());
      return;
    }
  }
}
