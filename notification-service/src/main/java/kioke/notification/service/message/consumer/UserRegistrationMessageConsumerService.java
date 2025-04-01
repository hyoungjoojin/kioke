package kioke.notification.service.message.consumer;

import java.util.ArrayList;
import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.service.message.AbstractMessageConsumerService;
import kioke.notification.configuration.MessageBrokerConfiguration;
import kioke.notification.model.User;
import kioke.notification.repository.UserRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationMessageConsumerService
    extends AbstractMessageConsumerService<UserRegistrationMessageDto> {

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

    User user = User.builder().userId(userId).issuedNotifications(new ArrayList<>()).build();
    userRepository.save(user);
  }

  @Override
  protected MessageConverter getMessageConverter() {
    return messageBrokerConfiguration.userRegistrationMessageConverter();
  }
}
