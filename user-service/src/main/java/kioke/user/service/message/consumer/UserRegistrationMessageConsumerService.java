package kioke.user.service.message.consumer;

import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.service.message.AbstractMessageConsumerService;
import kioke.user.configuration.MessageBrokerConfiguration;
import kioke.user.model.User;
import kioke.user.repository.UserRepository;
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

    User user =
        User.builder()
            .uid(messageDto.userId())
            .email(messageDto.email())
            .firstName(messageDto.firstName())
            .lastName(messageDto.lastName())
            .build();
    userRepository.save(user);
  }

  @Override
  protected MessageConverter getMessageConverter() {
    return messageBrokerConfiguration.userRegistrationMessageConverter();
  }
}
