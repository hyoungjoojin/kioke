package kioke.commons.dto.message.notification;

import java.util.List;
import kioke.commons.dto.message.notification.payload.NotificationMessagePayloadDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public record NotificationMessageDto<T extends NotificationMessagePayloadDto>(
    NotificationMessageType type, List<NotificationMessageTarget> targets, T payload) {

  public NotificationMessageDto(
      NotificationMessageType type, List<NotificationMessageTarget> targets, T payload) {
    if (!payload.getClass().equals(type.getClazz())) {
      throw new IllegalArgumentException();
    }

    this.type = type;
    this.targets = targets;
    this.payload = payload;
  }

  public static class NotificationMessageConverter implements MessageConverter {

    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    public NotificationMessageConverter() {
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
