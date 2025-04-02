package kioke.journal.service;

import java.util.List;
import java.util.Optional;
import kioke.journal.model.Bookmark;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import kioke.journal.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

  @Autowired private BookmarkRepository bookmarkRepository;

  public boolean isBookmarked(User user, Journal journal) {
    return bookmarkRepository.findByUserAndJournal(user, journal).isPresent();
  }

  public List<Journal> getBookmarks(User user) {
    return user.getBookmarks().stream().map(bookmark -> bookmark.getJournal()).toList();
  }

  public void addBookmark(User user, Journal journal) {
    Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndJournal(user, journal);

    if (existingBookmark.isEmpty()) {
      Bookmark bookmark = Bookmark.builder().user(user).journal(journal).build();
      bookmarkRepository.save(bookmark);
    }
  }

  public void deleteBookmark(User user, Journal journal) {
    Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndJournal(user, journal);

    if (bookmark.isPresent()) {
      bookmarkRepository.deleteById(bookmark.get().getId());
    }
  }
}
