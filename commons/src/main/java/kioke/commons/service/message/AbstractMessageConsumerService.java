package kioke.commons.service.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;

public abstract class AbstractMessageConsumerService<T> {

  public abstract void receive(Message message);

  protected T deserialize(Message message) {
    @SuppressWarnings("unchecked")
    T data = (T) getMessageConverter().fromMessage(message);

    return data;
  }

  protected abstract MessageConverter getMessageConverter();
}
