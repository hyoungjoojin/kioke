package kioke.journal.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import kioke.journal.constant.Permission;
import kioke.journal.constant.Role;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import kioke.journal.model.UserJournalMetadata;
import kioke.journal.repository.UserJournalMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserJournalMetadataService {

  private final UserJournalMetadataRepository userJournalMetadataRepository;

  public UserJournalMetadataService(UserJournalMetadataRepository userJournalMetadataRepository) {
    this.userJournalMetadataRepository = userJournalMetadataRepository;
  }

  @Transactional(readOnly = true)
  public List<JournalPreviewDto> getJournals(String userId, boolean findOnlyBookmarkedJournals) {
    log.debug("start fetching journals from database");
    List<JournalPreviewDto> journals =
        userJournalMetadataRepository.findAllJournalsByUser(userId, findOnlyBookmarkedJournals);
    log.debug("finished fetching journals from database");

    return journals;
  }

  @Transactional
  public Optional<UserJournalMetadata> getJournal(String userId, String journalId) {
    Optional<UserJournalMetadata> userJournalMetadata =
        userJournalMetadataRepository.findByUserIdAndJournalId(userId, journalId);

    if (userJournalMetadata.isPresent()) {
      userJournalMetadata.get().setLastViewed(Instant.now());
      userJournalMetadataRepository.save(userJournalMetadata.get());
    }

    return userJournalMetadata;
  }

  @Transactional(readOnly = true)
  public boolean hasPermission(String userId, String journalId, Permission permission) {
    return userJournalMetadataRepository
        .findByUserIdAndJournalId(userId, journalId)
        .map(metadata -> metadata.getRole().hasPermission(permission))
        .orElse(false);
  }

  @Transactional
  public void setRole(User user, Journal journal, Role role) {
    UserJournalMetadata userJournalMetadata =
        userJournalMetadataRepository
            .findByUserIdAndJournalId(user.getUserId(), journal.getJournalId())
            .orElse(
                UserJournalMetadata.builder()
                    .user(user)
                    .journal(journal)
                    .role(null)
                    .isBookmarked(false)
                    .lastViewed(Instant.now())
                    .build());

    if (!role.equals(userJournalMetadata.getRole())) {
      userJournalMetadata.setRole(role);
      userJournalMetadataRepository.save(userJournalMetadata);
    }
  }

  @Transactional
  public void deleteRole(String userId, String journalId) {
    userJournalMetadataRepository
        .findByUserIdAndJournalId(userId, journalId)
        .ifPresent(
            role -> {
              userJournalMetadataRepository.delete(role);
            });
  }

  @Transactional
  public void addBookmark(String userId, String journalId) {
    Optional<UserJournalMetadata> userJournalMetadata =
        userJournalMetadataRepository.findByUserIdAndJournalId(userId, journalId);

    if (userJournalMetadata.isPresent()) {
      userJournalMetadata.get().setIsBookmarked(true);
      userJournalMetadataRepository.save(userJournalMetadata.get());
    }
  }

  @Transactional
  public void deleteBookmark(String userId, String journalId) {
    Optional<UserJournalMetadata> userJournalMetadata =
        userJournalMetadataRepository.findByUserIdAndJournalId(userId, journalId);

    if (userJournalMetadata.isPresent()) {
      userJournalMetadataRepository.delete(userJournalMetadata.get());
    }
  }
}
