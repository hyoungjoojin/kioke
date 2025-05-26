package kioke.user.feature.user.service;

import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.service.message.AbstractMessageConsumerService;
import kioke.user.config.MessageBrokerConfig;
import kioke.user.feature.user.domain.User;
import kioke.user.feature.user.repository.UserRepository;
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

  @Autowired private MessageBrokerConfig messageBrokerConfiguration;

  @Autowired public Queue userRegistrationQueue;

  @Override
  @RabbitListener(queues = "#{userRegistrationQueue.getName()}", autoStartup = "true")
  public void receive(Message message) {
    UserRegistrationMessageDto messageDto = deserialize(message);

    User user =
        new User(
            messageDto.userId(),
            messageDto.email(),
            messageDto.firstName() + " " + messageDto.lastName());
    userRepository.save(user);
  }

  @Override
  protected MessageConverter getMessageConverter() {
    return messageBrokerConfiguration.userRegistrationMessageConverter();
  }
}
