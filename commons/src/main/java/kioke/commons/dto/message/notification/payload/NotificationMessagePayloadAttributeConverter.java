package kioke.commons.dto.message.notification.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class NotificationMessagePayloadAttributeConverter
    implements AttributeConverter<NotificationMessagePayloadDto, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(NotificationMessagePayloadDto payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public NotificationMessagePayloadDto convertToEntityAttribute(String string) {
    try {
      return objectMapper.readValue(string, NotificationMessagePayloadDto.class);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException();
    }
  }
}
