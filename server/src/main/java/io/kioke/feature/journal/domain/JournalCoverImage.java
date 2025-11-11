package io.kioke.feature.journal.domain;

import io.kioke.feature.image.domain.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOURNAL_COVER_IMAGE_TABLE")
public class JournalCoverImage {

  @Id
  @Column(name = "JOURNAL_ID")
  private String journalId;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId("JOURNAL_ID")
  @JoinColumn(name = "JOURNAL_ID")
  private Journal journal;

  @OneToOne(fetch = FetchType.LAZY)
  private Image image;

  public static JournalCoverImage of(Journal journal, Image image) {
    JournalCoverImage coverImage = new JournalCoverImage();
    coverImage.journalId = journal.getId();
    coverImage.image = image;
    return coverImage;
  }

  public Image getImage() {
    return image;
  }
}
