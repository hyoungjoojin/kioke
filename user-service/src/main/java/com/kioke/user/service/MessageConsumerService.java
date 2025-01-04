package com.kioke.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kioke.user.dto.message.CreateJournalMessageDto;
import com.kioke.user.exception.UserDoesNotExistException;
import com.kioke.user.exception.UserDoesNotExistException.UserIdentifierType;
import com.kioke.user.model.Journal;
import com.kioke.user.model.User;
import com.kioke.user.repository.JournalRepository;
import com.kioke.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumerService {
  @Autowired @Lazy private UserRepository userRepository;
  @Autowired @Lazy private JournalRepository journalRepository;

  @RabbitListener(queues = "journals.created")
  public void listenForCreateJournalEvent(String message) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      CreateJournalMessageDto data = objectMapper.readValue(message, CreateJournalMessageDto.class);
      final String uid = data.getUid();
      final String jid = data.getJid();

      User user =
          userRepository
              .findById(uid)
              .orElseThrow(
                  () -> {
                    return new UserDoesNotExistException(UserIdentifierType.UID, uid);
                  });
      Journal journal = Journal.builder().jid(jid).owner(user).build();
      journalRepository.save(journal);

    } catch (JsonProcessingException e) {
      log.error("Error while processing RabbitMQ message: ", e.toString());
    } catch (UserDoesNotExistException e) {
      log.error("User not found for message in RabbitMQ: ", e.toString());
    }
  }
}
