package kioke.commons.dto.message.notification;

import kioke.commons.dto.message.notification.payload.NotificationMessagePayloadDto;

public enum NotificationMessageType {
  ;

  private Class<? extends NotificationMessagePayloadDto> clazz;

  private NotificationMessageType(Class<? extends NotificationMessagePayloadDto> clazz) {
    this.clazz = clazz;
  }

  public Class<? extends NotificationMessagePayloadDto> getClazz() {
    return clazz;
  }
}
