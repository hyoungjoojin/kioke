package kioke.journal.service.message.consumer;

import java.util.ArrayList;
import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.service.message.AbstractMessageConsumerService;
import kioke.journal.configuration.MessageBrokerConfiguration;
import kioke.journal.model.User;
import kioke.journal.repository.UserRepository;
import kioke.journal.service.ShelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRegistrationMessageConsumerService
    extends AbstractMessageConsumerService<UserRegistrationMessageDto> {

  @Autowired private ShelfService shelfService;

  @Autowired private UserRepository userRepository;

  @Autowired private MessageBrokerConfiguration messageBrokerConfiguration;

  @Autowired public Queue userRegistrationQueue;

  @Override
  @RabbitListener(queues = "#{userRegistrationQueue.getName()}", autoStartup = "true")
  public void receive(Message message) {
    UserRegistrationMessageDto messageDto = deserialize(message);

    String userId = messageDto.userId();
    if (userRepository.findById(userId).isPresent()) {
      return;
    }

    User user = User.builder().userId(userId).journals(new ArrayList<>()).build();
    user = userRepository.save(user);

    shelfService.createArchive(user);
    shelfService.createShelf(userId, "My Bookshelf");
  }

  @Override
  protected MessageConverter getMessageConverter() {
    return messageBrokerConfiguration.userRegistrationMessageConverter();
  }
}
