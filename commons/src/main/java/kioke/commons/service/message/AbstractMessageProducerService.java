package kioke.commons.service.message;

public abstract class AbstractMessageProducerService<T> {

  public abstract void send(T object);
}
