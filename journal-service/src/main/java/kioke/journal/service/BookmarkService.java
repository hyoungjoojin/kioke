package kioke.journal.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kioke.journal.model.Bookmark;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import kioke.journal.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkService {

  private final BookmarkRepository bookmarkRepository;

  public BookmarkService(BookmarkRepository bookmarkRepository) {
    this.bookmarkRepository = bookmarkRepository;
  }

  @Transactional(readOnly = true)
  public List<String> getBookmarkedJournalIds(String userId) {
    return bookmarkRepository.findAllJournalIdsByUser(userId);
  }

  @Transactional(readOnly = true)
  public boolean isJournalBookmarked(String userId, String journalId) {
    Objects.requireNonNull(userId, "User ID should not be null.");
    Objects.requireNonNull(journalId, "Journal ID should not be null.");

    return bookmarkRepository.findByUserIdAndJournalId(userId, journalId).isPresent();
  }

  @Transactional
  public void addBookmark(User user, Journal journal) {
    Optional<Bookmark> existingBookmark =
        bookmarkRepository.findByUserIdAndJournalId(user.getUserId(), journal.getJournalId());

    if (existingBookmark.isEmpty()) {
      Bookmark bookmark = Bookmark.builder().user(user).journal(journal).build();
      bookmarkRepository.save(bookmark);
    }
  }

  @Transactional
  public void deleteBookmark(User user, Journal journal) {
    Optional<Bookmark> bookmark =
        bookmarkRepository.findByUserIdAndJournalId(user.getUserId(), journal.getJournalId());

    if (bookmark.isPresent()) {
      bookmarkRepository.deleteById(bookmark.get().getId());
    }
  }
}
