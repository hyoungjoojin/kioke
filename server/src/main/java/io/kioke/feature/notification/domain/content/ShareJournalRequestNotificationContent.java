package io.kioke.feature.notification.domain.content;

import io.kioke.feature.journal.domain.ShareRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SHARE_JOURNAL_REQUEST_NOTIFICATION_CONTENT_TABLE")
public class ShareJournalRequestNotificationContent extends NotificationContent {

  @OneToOne
  @JoinColumn(name = "JOURNAL_SHARE_REQUEST_ID")
  private ShareRequest shareRequest;

  protected ShareJournalRequestNotificationContent() {}

  private ShareJournalRequestNotificationContent(ShareRequest shareRequest) {
    this.shareRequest = shareRequest;
  }

  public ShareRequest getShareRequest() {
    return shareRequest;
  }

  public static ShareJournalRequestNotificationContent of(ShareRequest shareRequest) {
    return new ShareJournalRequestNotificationContent(shareRequest);
  }
}
