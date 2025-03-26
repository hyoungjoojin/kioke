package kioke.commons.service.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMessageProducerService<T> {

  @Autowired private RabbitTemplate rabbitTemplate;

  public void send(T object) {
    rabbitTemplate.convertAndSend(
        getExchange().getName(),
        getRoutingKey(),
        getMessageConverter().toMessage(object, new MessageProperties()));
  }

  protected abstract Queue getQueue();

  protected abstract Binding getBinding();

  protected abstract Exchange getExchange();

  protected abstract String getRoutingKey();

  protected abstract MessageConverter getMessageConverter();
}
