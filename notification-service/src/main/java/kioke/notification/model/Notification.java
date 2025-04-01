package kioke.notification.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import kioke.commons.dto.message.notification.NotificationMessageType;
import kioke.commons.dto.message.notification.payload.NotificationMessagePayloadAttributeConverter;
import kioke.commons.dto.message.notification.payload.NotificationMessagePayloadDto;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "NOTIFICATION_TABLE")
@Data
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String notificationId;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private User issuedTo;

  @Enumerated(EnumType.STRING)
  private NotificationMessageType type;

  @Convert(converter = NotificationMessagePayloadAttributeConverter.class)
  private NotificationMessagePayloadDto payload;

  private boolean read;

  @CreatedDate private OffsetDateTime createdAt;
}
