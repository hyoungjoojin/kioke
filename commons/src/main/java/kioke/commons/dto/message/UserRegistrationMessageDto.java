package kioke.commons.dto.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public record UserRegistrationMessageDto(
    String userId, String email, String firstName, String lastName) {

  public UserRegistrationMessageDto(
      String userId, String email, String firstName, String lastName) {
    this.userId = userId;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static class UserRegistrationMessageConverter implements MessageConverter {

    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    public UserRegistrationMessageConverter() {
      jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties)
        throws MessageConversionException {
      return jackson2JsonMessageConverter.toMessage(object, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
      return jackson2JsonMessageConverter.fromMessage(message);
    }
  }
}
