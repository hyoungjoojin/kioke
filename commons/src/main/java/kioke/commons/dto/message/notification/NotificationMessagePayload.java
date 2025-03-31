package kioke.commons.dto.message.notification;

public abstract class NotificationMessagePayload {

  protected NotificationMessageType type;

  public abstract String convertToString();

  public abstract NotificationMessagePayload convertToEntity();
}
