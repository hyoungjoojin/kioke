package kioke.notification.service.message.consumer;

import com.rabbitmq.client.AMQP.Queue;
import kioke.commons.dto.message.notification.NotificationMessageDto;
import kioke.commons.dto.message.notification.NotificationMessagePayload;
import kioke.commons.service.message.AbstractMessageConsumerService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationMessageConsumerService<T extends NotificationMessagePayload>
    extends AbstractMessageConsumerService<NotificationMessageDto<T>> {

  @Autowired
  @SuppressWarnings("unused")
  private Queue notificationQueue;

  @Autowired private MessageConverter notificationMessageConverter;

  @Override
  @RabbitListener(queues = "#{notificationQueue.getName()}", autoStartup = "true")
  public void receive(Message message) {
    NotificationMessageDto<T> messageDto = deserialize(message);
  }

  @Override
  protected MessageConverter getMessageConverter() {
    return notificationMessageConverter;
  }
}
