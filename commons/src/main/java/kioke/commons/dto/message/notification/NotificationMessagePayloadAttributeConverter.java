package kioke.commons.dto.message.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class NotificationMessagePayloadAttributeConverter
    implements AttributeConverter<NotificationMessagePayload, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(NotificationMessagePayload payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public NotificationMessagePayload convertToEntityAttribute(String string) {
    try {
      return objectMapper.readValue(string, NotificationMessagePayload.class);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException();
    }
  }
}
